package org.prgrms.devcourse.customer;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
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
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcCustomerRepositoryTest {

    @Configuration
    @ComponentScan(basePackages = {"org.prgrms.devcourse.customer"})
    static class JdbcCustomerRepositoryTestConfig {

        @Bean
        public DataSource dataSource() {
            DataSource dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:3306/order_mgmt")
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
        public JdbcCustomerRepository jdbcCustomerRepository(NamedParameterJdbcTemplate jdbcTemplate) {
            return new JdbcCustomerRepository(jdbcTemplate);
        }
    }

    @Autowired
    JdbcCustomerRepository jdbcCustomerRepository;

    @Autowired
    DataSource dataSource;

    Customer newCustomer;

    @BeforeAll
    void setup() {
        newCustomer = new Customer(
                UUID.randomUUID(),
                "sezikim",
                "sezikim@gmail.com",
                LocalDateTime.now()
        );
    }

    @BeforeEach
    void insertInitCustomer() {
        jdbcCustomerRepository.save(newCustomer);
    }

    @AfterEach
    void clean() {
        jdbcCustomerRepository.deleteAll();
    }

    @Test
    @DisplayName("고객을 정상적으로 insert할 수 있다.")
    void testInsertCustomer() {
        Customer testCustomer = new Customer(UUID.randomUUID(), "test", "test@gmail.com", LocalDateTime.now());

        jdbcCustomerRepository.save(testCustomer);

        assertThat(jdbcCustomerRepository.findAll(), hasSize(2));
    }

    @Test
    @DisplayName("중복되는 email을 갖는 고객을 insert할 수 없다.")
    void testDuplicateEmailCustomerInsert() {
        Customer testCustomer = new Customer(UUID.randomUUID(), "test", newCustomer.getEmail(), LocalDateTime.now());

        assertThrows(RuntimeException.class, () -> jdbcCustomerRepository.save(testCustomer));
    }

    @Test
    @DisplayName("insert한 고객을 정상적으로 조회할 수 있다.")
    void testFindCustomer() {
        var retrievedCustomer1 = jdbcCustomerRepository.findById(newCustomer.getCustomerId());
        var retrievedCustomer2 = jdbcCustomerRepository.findByEmail(newCustomer.getEmail());

        assertThat(retrievedCustomer1.isEmpty(), is(false));
        assertThat(retrievedCustomer2.isEmpty(), is(false));
        assertThat(retrievedCustomer1.get(), samePropertyValuesAs(retrievedCustomer2.get()));
    }

    @Test
    @DisplayName("고객을 정상적으로 update할 수 있다.")
    void testUpdate() {
        var updateCustomer = new Customer(newCustomer.getCustomerId(), newCustomer.getName(), newCustomer.getEmail(), LocalDateTime.now());

    }

    @Test
    @DisplayName("고객을 모두 조회할 수 있다.")
    void testFindAll() {
        var testCustomer1 = new Customer(UUID.randomUUID(), "test1", "test1@gmail.com", LocalDateTime.now());
        var testCustomer2 = new Customer(UUID.randomUUID(), "test2", "test2@gmail.com", LocalDateTime.now());

        jdbcCustomerRepository.save(testCustomer1);
        jdbcCustomerRepository.save(testCustomer2);

        assertThat(jdbcCustomerRepository.findAll(), hasSize(3));
    }

}