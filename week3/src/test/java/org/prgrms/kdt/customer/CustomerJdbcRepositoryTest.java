package org.prgrms.kdt.customer;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerJdbcRepositoryTest {

    @Configuration
    @ComponentScan(basePackages = {"org.prgrms.kdt.customer"})
    static class Config {

        @Bean
        public DataSource dataSource() {
            var dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost/order_mgmt")
                    .username("root")
                    .password("1234")
                    .type(HikariDataSource.class)
                    .build();
            return dataSource;
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }
    }

    @Autowired
    CustomerJdbcRepository customerJdbcRepository;

    @Autowired
    DataSource dataSource;

    Customer newCustomer;

    @BeforeAll
    void setup() {
        customerJdbcRepository.deleteAll();
        newCustomer = new Customer(UUID.randomUUID(), "test-user", "test-user@gamil.com", LocalDateTime.now());
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

        var customerId = newCustomer.getCustomerId();
        var retrievedCustomer = customerJdbcRepository.findById(newCustomer.getCustomerId());

        assertThat(retrievedCustomer.isEmpty(), is(false));
        assertThat(retrievedCustomer.get(), samePropertyValuesAs(newCustomer));
    }

    @Test
    @DisplayName("전체 고객을 조회할 수 있다.")
    @Order(3)
    public void testFindAll() {
        var customers = customerJdbcRepository.findAll();
        assertThat(customers.isEmpty(), is(false));
    }

    @Test
    @DisplayName("이름으로 고객을 조회할 수 있다.")
    @Order(4)
    public void testFindByName() {
        var customers = customerJdbcRepository.findByName(newCustomer.getName());
        assertThat(customers.isEmpty(), is(false));

        var unknown = customerJdbcRepository.findByName("unknown-user");
        assertThat(unknown.isEmpty(), is(true));
    }

    @Test
    @DisplayName("이메일로 고객을 조회할 수 있다.")
    @Order(5)
    public void testFindByEmail() {
        var customers = customerJdbcRepository.findByEmail(newCustomer.getEmail());
        assertThat(customers.isEmpty(), is(false));

        var unknown = customerJdbcRepository.findByEmail("unknown-user@gamil.com");
        assertThat(unknown.isEmpty(), is(true));
    }

    @Test
    @DisplayName("고객을 수정할 수 있다.")
    @Order(6)
    public void testUpdate() {
        newCustomer.changeName("updated-user");
        customerJdbcRepository.update(newCustomer);

        var all = customerJdbcRepository.findAll();
        assertThat(all, hasSize(1));
        assertThat(all, everyItem(samePropertyValuesAs(newCustomer)));

        var retrievedCustomer = customerJdbcRepository.findById(newCustomer.getCustomerId());
        assertThat(retrievedCustomer.isEmpty(), is(false));
        assertThat(retrievedCustomer.get(), samePropertyValuesAs(newCustomer));
    }


}