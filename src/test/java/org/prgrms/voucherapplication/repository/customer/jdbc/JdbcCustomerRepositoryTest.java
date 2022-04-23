package org.prgrms.voucherapplication.repository.customer.jdbc;

import com.wix.mysql.EmbeddedMysql;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.prgrms.voucherapplication.entity.Customer;
import org.prgrms.voucherapplication.entity.SqlVoucher;
import org.prgrms.voucherapplication.repository.customer.jdbc.JdbcCustomerRepository;
import org.prgrms.voucherapplication.repository.voucher.jdbc.JdbcVoucherRepository;
import org.prgrms.voucherapplication.view.io.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

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
import static org.hamcrest.Matchers.samePropertyValuesAs;

@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcCustomerRepositoryTest {

    @Configuration
    @ComponentScan(basePackages = {"org.prgrms.voucherapplication.repository.customer.jdbc"})
    static class Config {

        @Bean
        public DataSource dataSource() {
            var dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/test-voucher_mgmt")
                    .username("test")
                    .password("test1234!")
                    .type(HikariDataSource.class)
                    .build();

            return dataSource;
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }

        @Bean
        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(JdbcTemplate jdbcTemplate) {
            return new NamedParameterJdbcTemplate(jdbcTemplate);
        }

        @Bean
        public JdbcCustomerRepository jdbcCustomerRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
            return new JdbcCustomerRepository(namedParameterJdbcTemplate);
        }

        @Bean
        public JdbcVoucherRepository jdbcVoucherRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
            return new JdbcVoucherRepository(namedParameterJdbcTemplate);
        }
    }

    @Autowired
    JdbcCustomerRepository jdbcCustomerRepository;

    @Autowired
    JdbcVoucherRepository jdbcVoucherRepository;

    EmbeddedMysql embeddedMysql;

    @BeforeAll
    void setUp() {
        var mysqldConfig = aMysqldConfig(v8_0_11)
                .withCharset(UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();

        embeddedMysql = anEmbeddedMysql(mysqldConfig)
                .addSchema("test-voucher_mgmt", classPathScript("schema.sql"))
                .start(); // server start
    }

    @AfterEach
    void  cleanUpDb() {
        jdbcCustomerRepository.deleteAll();
    }

    @AfterAll
    void cleanUp() {
        embeddedMysql.stop();
    }

    @Test
    @DisplayName("전체 고객 조회")
    void findAll() {
        var customer1 = new Customer(UUID.randomUUID(), "test-user1", "test-user1@gmail.com", LocalDateTime.now());
        var customer2 = new Customer(UUID.randomUUID(), "test-user2", "test-user2@gmail.com", LocalDateTime.now());
        var customer3 = new Customer(UUID.randomUUID(), "test-user3", "test-user3@gmail.com", LocalDateTime.now());

        jdbcCustomerRepository.insert(customer1);
        jdbcCustomerRepository.insert(customer2);
        jdbcCustomerRepository.insert(customer3);

        var customers = jdbcCustomerRepository.findAll();

        assertThat(customers.isEmpty(), is(false));
        assertThat(customers.get(), hasSize(3));
    }

    @Test
    @DisplayName("새 고객 추가")
    void insert() {
        var customer = new Customer(UUID.randomUUID(), "test-user1", "test-user1@gmail.com", LocalDateTime.now());

        jdbcCustomerRepository.insert(customer);

        Optional<Customer> customerById = jdbcCustomerRepository.findById(customer.getCustomerId());
        assertThat(customerById.isEmpty(), is(false));
    }

    @Test
    @DisplayName("고객 정보 변경: 고객 이름 변경")
    void updateName() {
        var customer = new Customer(UUID.randomUUID(), "test-user1", "test-user1@gmail.com", LocalDateTime.now());
        jdbcCustomerRepository.insert(customer);
        customer.changeName("test-user1-changed");

        jdbcCustomerRepository.update(customer);

        Optional<Customer> customerById = jdbcCustomerRepository.findById(customer.getCustomerId());
        assertThat(customerById.isEmpty(), is(false));
        assertThat(customerById.get(), samePropertyValuesAs(customer));
    }

    @Test
    @DisplayName("고객 정보 변경: 고객 이메일 변경 (중복X)")
    void updateNonDuplicateEmail() {
        var customer1 = new Customer(UUID.randomUUID(), "test-user1", "test-user1@gmail.com", LocalDateTime.now());
        var customer2 = new Customer(UUID.randomUUID(), "test-user2", "test-user2@gmail.com", LocalDateTime.now());
        jdbcCustomerRepository.insert(customer1);
        jdbcCustomerRepository.insert(customer2);
        customer1.changeEmail("test-user1-changed@gmail.com");

        jdbcCustomerRepository.update(customer1);

        Optional<Customer> customerById = jdbcCustomerRepository.findById(customer1.getCustomerId());
        assertThat(customerById.isEmpty(), is(false));
        assertThat(customerById.get(), samePropertyValuesAs(customer1));
    }

    @Test
    @DisplayName("고객 정보 변경: 고객 이메일 변경 (중복 O)")
    void updateDuplicateEmail() {
        var customer1 = new Customer(UUID.randomUUID(), "test-user1", "test-user1@gmail.com", LocalDateTime.now());
        var customer2 = new Customer(UUID.randomUUID(), "test-user2", "test-user2@gmail.com", LocalDateTime.now());
        jdbcCustomerRepository.insert(customer1);
        jdbcCustomerRepository.insert(customer2);
        customer1.changeEmail("test-user2@gmail.com");

        Assertions.assertThrows(Exception.class, () -> {
            jdbcCustomerRepository.update(customer1);
        });

    }

    @Test
    @DisplayName("고객 id로 고객 조회")
    void findById() {
        var customer = new Customer(UUID.randomUUID(), "test-user1", "test-user1@gmail.com", LocalDateTime.now());

        jdbcCustomerRepository.insert(customer);

        Optional<Customer> customerById = jdbcCustomerRepository.findById(customer.getCustomerId());
        assertThat(customerById.isEmpty(), is(false));
        assertThat(customerById.get(), samePropertyValuesAs(customer));
    }

    @Test
    @DisplayName("고객 이름으로 고객 조회")
    void findByName() {
        var customer = new Customer(UUID.randomUUID(), "test-user1", "test-user1@gmail.com", LocalDateTime.now());
        jdbcCustomerRepository.insert(customer);

        Optional<Customer> customerByName = jdbcCustomerRepository.findByName(customer.getName());
        assertThat(customerByName.isEmpty(), is(false));
        assertThat(customerByName.get(), samePropertyValuesAs(customer));
    }

    @Test
    @DisplayName("고객 이메일로 고객 조회")
    void findByEmail() {
        var customer = new Customer(UUID.randomUUID(), "test-user1", "test-user1@gmail.com", LocalDateTime.now());

        jdbcCustomerRepository.insert(customer);

        Optional<Customer> customerByEmail = jdbcCustomerRepository.findByEmail(customer.getEmail());
        assertThat(customerByEmail.isEmpty(), is(false));
        assertThat(customerByEmail.get(), samePropertyValuesAs(customer));
    }

    @Test
    @DisplayName("전체 고객 삭제")
    void deleteAll() {
        var customer1 = new Customer(UUID.randomUUID(), "test-user1", "test-user1@gmail.com", LocalDateTime.now());
        var customer2 = new Customer(UUID.randomUUID(), "test-user2", "test-user2@gmail.com", LocalDateTime.now());
        var customer3 = new Customer(UUID.randomUUID(), "test-user3", "test-user3@gmail.com", LocalDateTime.now());
        jdbcCustomerRepository.insert(customer1);
        jdbcCustomerRepository.insert(customer2);
        jdbcCustomerRepository.insert(customer3);

        var issuedFixedAmountVoucher = new SqlVoucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT.name(), 50, customer1.getCustomerId(), true, LocalDateTime.now(), LocalDateTime.now());
        jdbcVoucherRepository.insert(issuedFixedAmountVoucher);


        jdbcCustomerRepository.deleteAll();

        Optional<List<Customer>> customers = jdbcCustomerRepository.findAll();
        assertThat(customers.get(), hasSize(0));
        Optional<SqlVoucher> byId = jdbcVoucherRepository.findById(issuedFixedAmountVoucher.getVoucherId());
        assertThat(byId.isEmpty(), is(true));
    }
}