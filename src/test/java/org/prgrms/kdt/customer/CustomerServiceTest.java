package org.prgrms.kdt.customer;

import com.wix.mysql.EmbeddedMysql;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
    private static final Logger logger = LoggerFactory.getLogger(CustomerNamedJdbcRepository.class);

    @Configuration
    static class Config {
        @Bean
        public DataSource dataSource() {
            var dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost/order_mgmt")
                    .username("root")
                    .password("root1234!")
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
        public PlatformTransactionManager platformTransactionManager(DataSource dataSource) {
            return new DataSourceTransactionManager(dataSource);
        }

        @Bean
        public TransactionTemplate transactionTemplate(PlatformTransactionManager platformTransactionManager) {
            return new TransactionTemplate(platformTransactionManager);
        }

        @Bean
        public CustomerRepository customerRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
            return new CustomerNamedJdbcRepository(namedParameterJdbcTemplate);
        }

        @Bean
        public CustomerService customerService(CustomerRepository customerRepository) {
            return new CustomerServiceImpl(customerRepository);
        }
    }

//    @AfterEach
//    void dataCleanup() {
//        customerRepository.deleteAll();
//    }

    @Autowired
    CustomerService customerService;

    @Autowired
    CustomerRepository customerRepository;

    @Test
    @DisplayName("여러 건 추가 테스트")
    void multiInsertTest() {
        customerRepository.deleteAll();
        var customers = List.of(
                new Customer(UUID.randomUUID(), "a", "a@gmail.com", LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS)),
                new Customer(UUID.randomUUID(), "b", "b@gmail.com", LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS))
        );

        customerService.createCustomers(customers);
        var allCustomerRetrieved = customerRepository.findAll();

        assertThat(allCustomerRetrieved.size(), is(2));
        assertThat(allCustomerRetrieved,
                containsInAnyOrder(samePropertyValuesAs(customers.get(0)),
                        samePropertyValuesAs(customers.get(1))));
    }

    @Test
    @DisplayName("여러 건 추가 실패 시 전체 트랜잭션이 롤백되어야 한다.")
    void multiInsertRollbackTest() {
        customerRepository.deleteAll();
        var customers = List.of(
                new Customer(UUID.randomUUID(), "c", "c@gmail.com", LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS)),
                new Customer(UUID.randomUUID(), "d", "c@gmail.com", LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS))
        );

        try {
            customerService.createCustomers(customers);
        } catch (DataAccessException e) {}

        var allCustomerRetrieved = customerRepository.findAll();

        assertThat(allCustomerRetrieved.size(), is(0));
        assertThat(allCustomerRetrieved.isEmpty(), is(true));
        assertThat(allCustomerRetrieved,
                not(containsInAnyOrder(samePropertyValuesAs(customers.get(0)),
                        samePropertyValuesAs(customers.get(1)))));
    }
}