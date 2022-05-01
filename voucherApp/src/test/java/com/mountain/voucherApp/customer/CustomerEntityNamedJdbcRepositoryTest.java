package com.mountain.voucherApp.customer;

import com.mountain.voucherApp.adapter.out.persistence.customer.CustomerNamedJdbcRepository;
import com.mountain.voucherApp.adapter.out.persistence.voucher.JdbcVoucherRepository;
import com.mountain.voucherApp.adapter.out.persistence.voucher.VoucherEntity;
import com.mountain.voucherApp.application.port.in.CustomerDto;
import com.mountain.voucherApp.domain.vo.CustomerName;
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
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("prod")
class CustomerEntityNamedJdbcRepositoryTest {

    private static final Logger log = LoggerFactory.getLogger(CustomerEntityNamedJdbcRepositoryTest.class);

    @Configuration
    @ComponentScan(
            basePackages = {"com.mountain.voucherApp.adapter.out.persistence.customer", "com.mountain.voucherApp.adapter.out.persistence.voucher"}
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
        customer = new CustomerDto(UUID.randomUUID(),
                null,
                new CustomerName("test-user"),
                "test-user@gmail.com",
                null,
                LocalDateTime.now());

        voucherEntity = new VoucherEntity(UUID.randomUUID(),
                1,
                1000L);

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

    @AfterAll
    void cleanup() {
        embeddedMysql.stop();
    }

    @Test
    @Order(1)
    @Description("dataSource가 정상적으로 Autowired되었는지 확인.")
    public void testHikariConnectionPool() {
        assertThat(dataSource.getClass().getName(), is("com.zaxxer.hikari.HikariDataSource"));
    }

    @Test
    @DisplayName("고객을 추가할 수 있다.")
    @Order(2)
    public void testInsert() throws Exception {
        try {
            customerNamedJdbcRepository.insert(customer);
        } catch (BadSqlGrammarException e) {
            log.info("Got BadSqlGrammarException error code -> {}", e.getSQLException().getErrorCode(), e);
        }
        Optional<CustomerDto> savedCustomer = customerNamedJdbcRepository.findById(customer.getCustomerId());
        assertAll(
                () -> assertThat(savedCustomer.isEmpty(), is(false)),
                () -> assertThat(savedCustomer.get(), samePropertyValuesAs(customer))
        );
    }

    @Test
    @DisplayName("전체 고객 조회.")
    @Order(3)
    public void testFindAll() throws Exception {
        List<CustomerDto> customerEntities = customerNamedJdbcRepository.findAll();
        assertThat(customerEntities.isEmpty(), is(false));
    }

    @Test
    @DisplayName("이름으로 고객 조회.")
    @Order(4)
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
    @Order(5)
    public void testFindByEmail() throws Exception {
        Optional<CustomerDto> getCustomer = customerNamedJdbcRepository.findByEmail(customer.getEmail());
        Optional<CustomerDto> unknown = customerNamedJdbcRepository.findByEmail("unknown-user");

        assertAll(
                () -> assertThat(getCustomer.isEmpty(), is(false)),
                () -> assertThat(unknown.isEmpty(), is(true))
        );
    }

    @Test
    @DisplayName("고객을 수정할 수 있다.")
    @Order(6)
    public void testUpdate() throws Exception {
        jdbcVoucherRepository.insert(voucherEntity);
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
    @Order(7)
    public void testFindByVoucherId() throws Exception {
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
    @Order(8)
    public void testFindByVoucherIdListCase() throws Exception {
        CustomerDto customer2 = new CustomerDto(UUID.randomUUID(),
                voucherEntity.getVoucherId(),
                new CustomerName("test-user2"),
                "test-user2@gmail.com",
                null,
                LocalDateTime.now());
        customerNamedJdbcRepository.insert(customer2);
        List<CustomerDto> customerEntities = customerNamedJdbcRepository.findByVoucherId(voucherEntity.getVoucherId());
        assertAll(
                () -> assertThat(customerEntities.isEmpty(), is(false)),
                () -> assertThat(customerEntities.size(), is(2))
        );
    }

    @Test
    @DisplayName("voucher id가 not null인 고객만 조회 할 수 있어야 한.")
    @Order(9)
    public void testVoucherIdNotNullList() throws Exception {
        customerNamedJdbcRepository.updateVoucherId(customer.getCustomerId(), null);
        List<CustomerDto> customerEntities = customerNamedJdbcRepository.findByVoucherIdNotNull();
        assertAll(
                () -> assertThat(customerEntities.isEmpty(), is(false)),
                () -> assertThat(customerEntities.size(), is(1))
        );
    }

    @Test
    @DisplayName("customerId를 조건으로 voucherId를 update 할 수 있어야 한다.")
    @Order(10)
    public void updateVoucherId() throws Exception {
        Optional<CustomerDto> optionalCustomer2 = customerNamedJdbcRepository.findByName("test-user2");
        CustomerDto customer2 = optionalCustomer2.get();

        customerNamedJdbcRepository.updateVoucherId(customer.getCustomerId(), null);
        customerNamedJdbcRepository.updateVoucherId(customer2.getCustomerId(), voucherEntity.getVoucherId());
        Optional<CustomerDto> getCustomer = customerNamedJdbcRepository.findById(customer.getCustomerId());;

//        assertThat(getCustomer.isEmpty(), is(true));
//        assertThat(getCustomer.get().getVoucherId(), nullValue());
//        assertThat(customer2.getVoucherId(), is(voucherEntity.getVoucherId()));

        assertAll(
                () -> assertThat(getCustomer.isEmpty(), is(false)),
                () -> assertThat(getCustomer.get().getVoucherId(), nullValue()),
                () -> assertThat(customer2.getVoucherId(), is(voucherEntity.getVoucherId()))
        );
    }

    @Test
    @DisplayName("customer id로 삭제할 수 있어야 한다.")
    @Order(11)
    public void removeByCustomerId() throws Exception {
        UUID customerId = customer.getCustomerId();
        customerNamedJdbcRepository.removeByCustomerId(customerId);
        Optional<CustomerDto> selectedCustomer = customerNamedJdbcRepository.findById(customerId);
        int allSize = customerNamedJdbcRepository.findAll().size();
        assertAll(
                () -> assertThat(selectedCustomer.isEmpty(), is(true)),
                () -> assertThat(allSize, is(1))
        );
    }

}