package com.mountain.voucherApp.dao.customer;

import com.mountain.voucherApp.dao.voucher.JdbcVoucherRepository;
import com.mountain.voucherApp.dto.CustomerDto;
import com.mountain.voucherApp.model.VoucherEntity;
import com.mountain.voucherApp.model.enums.DiscountPolicy;
import com.mountain.voucherApp.model.vo.CustomerName;
import com.mountain.voucherApp.model.vo.Email;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.MysqldConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("prod")
class CustomerEntityNamedJdbcRepositoryTest {

    private static final Logger log = LoggerFactory.getLogger(CustomerEntityNamedJdbcRepositoryTest.class);

    @Configuration
    @ComponentScan(
            basePackages = {"com.mountain.voucherApp.dao.customer",
                    "com.mountain.voucherApp.dao.voucher"}
    )
    static class Config {

        @Bean
        public DataSource dataSource() {
            String URL = "jdbc:mysql://localhost:2215/test-order-mgmt";
            HikariDataSource dataSource = DataSourceBuilder.create()
                    .url(URL)
                    .username("test")
                    .password("test1234!")
                    .type(HikariDataSource.class)
                    .build();
            return dataSource;
        }

        @Bean
        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(JdbcTemplate jdbcTemplate) {
            return new NamedParameterJdbcTemplate(jdbcTemplate);
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }

        @Bean
        public PlatformTransactionManager platformTransactionManager(DataSource dataSource) {
            return new DataSourceTransactionManager(dataSource);
        }

        @Bean
        public TransactionTemplate transactionTemplate(PlatformTransactionManager platformTransactionManager) {
            return new TransactionTemplate(platformTransactionManager);
        }
    }

    @Autowired
    CustomerNamedJdbcRepository customerNamedJdbcRepository;

    @Autowired
    JdbcVoucherRepository jdbcVoucherRepository;

    @Autowired
    DataSource dataSource;

    CustomerDto customer;
    VoucherEntity voucherEntity;
    EmbeddedMysql embeddedMysql;

    @BeforeAll
    void setUp() {
        MysqldConfig config = aMysqldConfig(v8_0_11)
                .withCharset(UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();

        embeddedMysql = anEmbeddedMysql(config)
                .addSchema("test-order-mgmt", classPathScript("schema.sql"))
                .start();
    }

    @BeforeEach
    void init() {
        customer = generateCustomer();
        voucherEntity = new VoucherEntity(UUID.randomUUID(), DiscountPolicy.FIXED, 1000L);
        jdbcVoucherRepository.insert(voucherEntity);
        customerNamedJdbcRepository.insert(customer);
    }

    @AfterEach
    void clear() {
        customerNamedJdbcRepository.deleteAll();
        jdbcVoucherRepository.deleteById(voucherEntity.getVoucherId());
    }


    static int seq = 1;
    private CustomerDto generateCustomer() {
        String name = "test" + seq++;
        String email = name + "@gmail.com";
        return new CustomerDto(UUID.randomUUID(),
                null,
                new CustomerName(name),
                new Email(email),
                null,
                LocalDateTime.now());
    }

    @AfterAll
    void cleanup() {
        embeddedMysql.stop();
    }

    @Test
    @Description("dataSource가 정상적으로 Autowired되었는지 확인.")
    public void testHikariConnectionPool() {
        assertThat(dataSource.getClass().getName(), is("com.zaxxer.hikari.HikariDataSource"));
    }

    @Test
    @DisplayName("고객을 추가할 수 있다.")
    public void testInsert() {
        CustomerDto customer2 = generateCustomer();
        try {
            customerNamedJdbcRepository.insert(customer2);
        } catch (BadSqlGrammarException e) {
            log.info("Got BadSqlGrammarException error code -> {}", e.getSQLException().getErrorCode(), e);
        }
        Optional<CustomerDto> savedCustomer = customerNamedJdbcRepository.findById(customer2.getCustomerId());
        assertAll(
                () -> assertThat(savedCustomer.isEmpty(), is(false)),
                () -> assertThat(savedCustomer.get(), samePropertyValuesAs(customer2))
        );
    }

    @Test
    @DisplayName("전체 고객 조회.")
    public void testFindAll() throws Exception {
        List<CustomerDto> customerEntities = customerNamedJdbcRepository.findAll();
        assertThat(customerEntities.isEmpty(), is(false));
    }

    @Test
    @DisplayName("이름으로 고객 조회.")
    public void testFindByName() {
        Optional<CustomerDto> getCustomer = customerNamedJdbcRepository.findByName(customer.getCustomerName());
        Optional<CustomerDto> unknown = customerNamedJdbcRepository.findByName("unknown-user");
        assertAll(
                () -> assertThat(getCustomer.isEmpty(), is(false)),
                () -> assertThat(unknown.isEmpty(), is(true))
        );
    }

    @Test
    @DisplayName("이메일로 고객 조회.")
    public void testFindByEmail() throws Exception {
        Optional<CustomerDto> getCustomer = customerNamedJdbcRepository.findByEmail(customer.getEmail().getAddress());
        Optional<CustomerDto> unknown = customerNamedJdbcRepository.findByEmail("unknown-user");

        assertAll(
                () -> assertThat(getCustomer.isEmpty(), is(false)),
                () -> assertThat(unknown.isEmpty(), is(true))
        );
    }

    @Test
    @DisplayName("고객을 수정할 수 있다.")
    public void testUpdate() {
        CustomerDto updateCustomer = new CustomerDto(
                customer.getCustomerId(),
                voucherEntity.getVoucherId(),
                new CustomerName("updated-user"),
                customer.getEmail(),
                customer.getLastLoginAt(),
                customer.getCreatedAt()
        );
        customerNamedJdbcRepository.update(updateCustomer);

        List<CustomerDto> customerEntities = customerNamedJdbcRepository.findAll();
        Optional<CustomerDto> savedCustomer = customerNamedJdbcRepository.findById(customer.getCustomerId());

        assertAll(
                () -> assertThat(customerEntities, hasSize(1)),
                () -> assertThat(savedCustomer.isEmpty(), is(false))
        );
    }

    @Test
    @DisplayName("voucherId로 고객 조회.")
    public void testFindByVoucherId() {
        customerNamedJdbcRepository.updateVoucherId(customer.getCustomerId(), voucherEntity.getVoucherId());

        List<CustomerDto> customerEntities = customerNamedJdbcRepository.findByVoucherId(voucherEntity.getVoucherId());
        List<CustomerDto> unknown = customerNamedJdbcRepository.findByVoucherId(UUID.randomUUID());

        assertAll(
                () -> assertThat(customerEntities.isEmpty(), is(false)),
                () -> assertThat(customerEntities.get(0).getVoucherId(), is(voucherEntity.getVoucherId())),
                () -> assertThat(unknown.isEmpty(), is(true))
        );
    }

    @Test
    @DisplayName("동일한 voucherId를 가진 2명 고객 조회.")
    public void testFindByVoucherIdListCase() {
        CustomerDto customer2 = generateCustomer();
        customerNamedJdbcRepository.insert(customer2);
        customerNamedJdbcRepository.updateVoucherId(customer.getCustomerId(), voucherEntity.getVoucherId());
        customerNamedJdbcRepository.updateVoucherId(customer2.getCustomerId(), voucherEntity.getVoucherId());

        List<CustomerDto> customerEntities = customerNamedJdbcRepository.findByVoucherId(voucherEntity.getVoucherId());
        assertAll(
                () -> assertThat(customerEntities.isEmpty(), is(false)),
                () -> assertThat(customerEntities.size(), is(2))
        );
    }

    @Test
    @DisplayName("voucherId가 not null인 고객만 조회 할 수 있어야 한.")
    public void testVoucherIdNotNullList() {
        customerNamedJdbcRepository.updateVoucherId(customer.getCustomerId(), voucherEntity.getVoucherId());

        List<CustomerDto> customerEntities = customerNamedJdbcRepository.findByVoucherIdNotNull();
        assertAll(
                () -> assertThat(customerEntities.isEmpty(), is(false)),
                () -> assertThat(customerEntities.size(), is(1))
        );
    }

    @Test
    @DisplayName("고객의 voucherId를 update 할 수 있어야 한다.")
    public void updateVoucherId() {
        customerNamedJdbcRepository.updateVoucherId(customer.getCustomerId(), voucherEntity.getVoucherId());

        Optional<CustomerDto> findCustomer = customerNamedJdbcRepository.findById(customer.getCustomerId());
        assertAll(
                () -> assertThat(findCustomer.isEmpty(), is(false)),
                () -> assertThat(findCustomer.get().getVoucherId(), is(voucherEntity.getVoucherId()))
        );
    }

    @Test
    @DisplayName("customer id로 삭제할 수 있어야 한다.")
    public void removeByCustomerId() {
        customerNamedJdbcRepository.removeByCustomerId(customer.getCustomerId());
        Optional<CustomerDto> selectedCustomer = customerNamedJdbcRepository.findById(customer.getCustomerId());
        assertAll(
                () -> assertThat(selectedCustomer.isEmpty(), is(true))
        );
    }

    @Test
    @DisplayName("해당 voucherId를 모두 null로 변경한다.")
    public void testUpdateAllByVoucherId() {
        CustomerDto customer2 = generateCustomer();
        customerNamedJdbcRepository.insert(customer2);
        customerNamedJdbcRepository.updateVoucherId(customer.getCustomerId(), voucherEntity.getVoucherId());
        customerNamedJdbcRepository.updateVoucherId(customer2.getCustomerId(), voucherEntity.getVoucherId());

        customerNamedJdbcRepository.removeVoucherId(voucherEntity.getVoucherId());
        List<CustomerDto> customers = customerNamedJdbcRepository.findByVoucherId(voucherEntity.getVoucherId());
        assertAll(
                () -> assertThat(customers.isEmpty(), is(true)),
                () -> assertThat(customers.size(), is(0))
        );

    }

}