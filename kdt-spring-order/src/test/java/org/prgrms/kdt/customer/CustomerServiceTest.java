package org.prgrms.kdt.customer;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
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
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerServiceTest {

    @Configuration
//    @EnableTransactionManagement
    static class Config {
        @Bean
        public DataSource dataSource() {
            HikariDataSource dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost/order_mgmt")
                    .username("root")
                    .password("admin")
                    .type(HikariDataSource.class)
                    .build();
            dataSource.setMaximumPoolSize(1000);
            dataSource.setMinimumIdle(100);
            return dataSource;
        }

        @Bean
        public NamedParameterJdbcTemplate jdbcTemplate() {
            return new NamedParameterJdbcTemplate(dataSource());
        }

        @Bean
        public PlatformTransactionManager platformTransactionManager() {
            return new DataSourceTransactionManager(dataSource());
        }

        @Bean
        public TransactionTemplate transactionTemplate() {
            return new TransactionTemplate(platformTransactionManager());
        }

        @Bean
        CustomerRepository customerRepository() {
            return new CustomerNamedJdbcRepository(jdbcTemplate(), transactionTemplate());
        }

        @Bean
        CustomerService customerService() {
            return new CustomerServiceImpl(customerRepository());
        }
    }

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerService customerService;

    @BeforeAll
    void setup() {
        customerRepository.deleteAll();
    }

    @AfterEach
    void cleanup() {
        customerRepository.deleteAll();
    }

    @Test
    @DisplayName("다건 추가 테스트")
    public void multiInsertTest() throws Exception {
        //given
        List<Customer> customers = List.of(
                new Customer(UUID.randomUUID(), "a", "a@gmail.com", LocalDateTime.now()),
                new Customer(UUID.randomUUID(), "b", "b@gmail.com", LocalDateTime.now())
        );
        //when
        customerService.createCustomers(customers);
        List<Customer> allCustomersRetrieved = customerRepository.findAll();

        //then
        assertThat(allCustomersRetrieved.size(), is(customers.size()));
        assertThat(allCustomersRetrieved, containsInAnyOrder(samePropertyValuesAs(customers.get(0)), samePropertyValuesAs(customers.get(1))));
    }

    @Test
    @DisplayName("다건 추가 실패시 전체 트랜잭션이 롤백되어야 한다")
    public void multiInsertFailTest() throws Exception {
        //given
        List<Customer> customers = List.of(
                new Customer(UUID.randomUUID(), "a", "c@gmail.com", LocalDateTime.now()),
                new Customer(UUID.randomUUID(), "b", "c@gmail.com", LocalDateTime.now())
        );
        //when
        try {
            customerService.createCustomers(customers);
        } catch (DataAccessException e) {

        }

        List<Customer> allCustomersRetrieved = customerRepository.findAll();

        //then
        assertThat(allCustomersRetrieved.isEmpty(), is(true));
        assertThat(allCustomersRetrieved, not(containsInAnyOrder(samePropertyValuesAs(customers.get(0)), samePropertyValuesAs(customers.get(1)))));
    }
}