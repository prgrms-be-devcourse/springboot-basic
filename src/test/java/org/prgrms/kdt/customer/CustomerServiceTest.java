package org.prgrms.kdt.customer;

import com.wix.mysql.EmbeddedMysql;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.prgrms.kdt.customer.repository.CustomerRepository;
import org.prgrms.kdt.customer.repository.JdbcCustomerRepository;
import org.prgrms.kdt.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerServiceTest {
    static EmbeddedMysql embeddedMysql;
    @Autowired
    CustomerService customerService;
    @Autowired
    CustomerRepository customerRepository;

    @BeforeAll
    static void setup() {
        final var mysqlConfig = aMysqldConfig(v8_0_11)
                .withCharset(UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMysql = anEmbeddedMysql(mysqlConfig)
                .addSchema("test-voucher", classPathScript("schema.sql"))
                .start();
    }

    @AfterAll
    static void cleanup() {
        embeddedMysql.stop();
    }

    @AfterEach
    void dataCleanup() {
        customerRepository.deleteAll();
    }

    @Test
    @DisplayName("다건 추가 테스트")
    void multiInsertTest() {
        final var customers = List.of(
                new Customer(UUID.randomUUID(), "a", "a@gmail.com", LocalDateTime.now(), LocalDateTime.now()),
                new Customer(UUID.randomUUID(), "b", "b@gmail.com", LocalDateTime.now(), LocalDateTime.now())
        );

        customerService.createCustomers(customers);
        final var allCustomersRetrieved = customerRepository.findAll();
        assertThat(allCustomersRetrieved.size(), is(2));
        assertThat(allCustomersRetrieved, containsInAnyOrder(samePropertyValuesAs(customers.get(0)), samePropertyValuesAs(customers.get(1))));
    }

    @Test
    @DisplayName("다건 추가 실패시 전체 트렌젝션이 롤백되야한다.")
    void multiInsertRollbackTest() {
        final var customers = List.of(
                new Customer(UUID.randomUUID(), "c", "c@gmail.com", LocalDateTime.now(), LocalDateTime.now()),
                new Customer(UUID.randomUUID(), "d", "c@gmail.com", LocalDateTime.now(), LocalDateTime.now())
        );

        try {
            customerService.createCustomers(customers);
        } catch (final DataAccessException e) {
        }

        final var allCustomersRetrieved = customerRepository.findAll();
        assertThat(allCustomersRetrieved.size(), is(0));
        assertThat(allCustomersRetrieved.isEmpty(), is(true));
        assertThat(allCustomersRetrieved, not(containsInAnyOrder(samePropertyValuesAs(customers.get(0)), samePropertyValuesAs(customers.get(1)))));
    }

    @Configuration
    @EnableTransactionManagement
    static class Config {

        @Bean
        public DataSource dataSource() {
            final var dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/test-voucher")
                    .username("test")
                    .password("test1234!")
                    .type(HikariDataSource.class)
                    .build();
            dataSource.setMaximumPoolSize(1000);
            dataSource.setMinimumIdle(100);
            return dataSource;
        }

        @Bean
        public JdbcTemplate jdbcTemplate(final DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }

        @Bean
        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(final JdbcTemplate jdbcTemplate) {
            return new NamedParameterJdbcTemplate(jdbcTemplate);
        }

        @Bean
        public PlatformTransactionManager platformTransactionManager(final DataSource dataSource) {
            return new DataSourceTransactionManager(dataSource);
        }

        @Bean
        public TransactionTemplate transactionTemplate(final PlatformTransactionManager platformTransactionManager) {
            return new TransactionTemplate(platformTransactionManager);
        }

        @Bean
        public CustomerRepository customerRepository(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
            return new JdbcCustomerRepository(namedParameterJdbcTemplate);
        }

        @Bean
        public CustomerService customerService(final CustomerRepository customerRepository) {
            return new CustomerService(customerRepository);
        }
    }
}