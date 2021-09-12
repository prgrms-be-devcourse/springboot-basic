package org.prgrms.kdt.domain.customer;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.random.RandomDataGenerator;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertAll;

@Slf4j
@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerJdbcRepositoryTest {

    @Autowired
    CustomerJdbcRepository customerJdbcRepository;

    @Autowired
    DataSource dataSource;

    Customer newCustomer;

    @BeforeAll
    void setUp() {
        newCustomer = new Customer(new RandomDataGenerator().nextLong(0, 10000),
                Name.valueOf("테스트"),
                Email.valueOf("test@gmail.com"),
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS),
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        customerJdbcRepository.deleteAll();
    }

    @Test
    @Order(1)
    public void testHikariConnectionPool() {
        assertThat(dataSource.getClass().getName(), is("com.zaxxer.hikari.HikariDataSource"));
    }

    @Test
    @Order(2)
    @DisplayName("고객을 추가할 수 있다.")
    public void testInsert() {
        customerJdbcRepository.insert(newCustomer);
        var retrievedCustomer = customerJdbcRepository.findById(newCustomer.getCustomerId());

        assertAll(
                () -> assertThat(retrievedCustomer.isEmpty(), is(false)),
                () -> assertThat(retrievedCustomer.get(), samePropertyValuesAs(newCustomer, "lastLoginAt"))
        );
    }

    @Test
    @Order(3)
    @DisplayName("전체 고객을 조회할 수 있다.")
    public void testFindAll() {
        var customers = customerJdbcRepository.findAll();

        assertThat(customers.isEmpty(), is(false));
    }

    @Test
    @Order(4)
    @DisplayName("고객을 아이디로 조회할 수 있다.")
    public void testFindById() {
        var customer = customerJdbcRepository.findById(newCustomer.getCustomerId());

        assertThat(customer.isEmpty(), is(false));
    }

    @Test
    @Order(5)
    @DisplayName("이름으로 고객을 조회할 수 있다.")
    public void testFindByName() {
        var customer = customerJdbcRepository.findByName(newCustomer.getName());
        var unknown = customerJdbcRepository.findByName(Name.valueOf("unknown"));

        assertAll(
                () -> assertThat(customer.isEmpty(), is(false)),
                () -> assertThat(unknown.isEmpty(), is(true))
        );

    }

    @Test
    @Order(6)
    @DisplayName("이메일로 고객을 조회할 수 있다.")
    public void testFindByEmail() {
        var customer = customerJdbcRepository.findByEmail(newCustomer.getEmail());
        var unknown = customerJdbcRepository.findByEmail(Email.valueOf("unknown-user@gmail.com"));

        assertAll(
                () -> assertThat(customer.isEmpty(), is(false)),
                () -> assertThat(unknown.isEmpty(), is(true))
        );
    }

    @Test
    @Order(7)
    @DisplayName("고객을 수정할 수 있다.")
    public void testUpdate() {
        newCustomer.getName().changeName("updatedName");
        customerJdbcRepository.update(newCustomer);
        var all = customerJdbcRepository.findAll();
        var retrievedCustomer = customerJdbcRepository.findById(newCustomer.getCustomerId());

        assertAll(
                () -> assertThat(all, hasSize(1)),
                () -> assertThat(all, everyItem(samePropertyValuesAs(newCustomer, "lastLoginAt"))),
                () -> assertThat(retrievedCustomer.isEmpty(), is(false)),
                () -> assertThat(retrievedCustomer.get(), samePropertyValuesAs(newCustomer, "lastLoginAt"))
        );
    }


    @Configuration
    @ComponentScan(
            basePackages = {"org.prgrms.kdt.domain",
                    "org.prgrms.kdt.common",
                    "org.prgrms.kdt.service"
            }
    )
    static class Config {

        @Bean
        public DataSource dataSource() {
            var dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:3306/order_mgmt?characterEncoding=utf8&serverTimezone=UTC")
                    .username("kdt")
                    .password("kdt")
                    .type(HikariDataSource.class)
                    .build();
            dataSource.setMaximumPoolSize(1000);
            dataSource.setMinimumIdle(100);
            return dataSource;
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
}

