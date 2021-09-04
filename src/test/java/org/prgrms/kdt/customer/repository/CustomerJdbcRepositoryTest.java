package org.prgrms.kdt.customer.repository;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.prgrms.kdt.customer.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
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
            return DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost/order_mgmt")
                    .username("root")
                    .password("xogud")
                    .type(HikariDataSource.class)
                    .build();
        }

    }

    @Autowired CustomerJdbcRepository customerJdbcRepository;
    @Autowired DataSource dataSource;
    Customer newCustomer;

    @BeforeAll
    void setUp() {
        newCustomer = new Customer(UUID.randomUUID(), "test-user", "test-user@gmail.com", LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
        customerJdbcRepository.deleteAll();
    }

    @Test
    @Order(1)
    public void testHikariConnectionPool() {
        assertThat(dataSource.getClass().getName(), is("com.zaxxer.hikari.HikariDataSource"));
    }

    @Test
    @DisplayName("고객을 추가할 수 있다.")
    @Order(2)
    public void testInsert() {
        customerJdbcRepository.deleteAll();;
        // 윈도우 / MAC 의 LocalDateTime 정밀도가 다르다.
        customerJdbcRepository.insert(newCustomer);

        var retrievedCustomer = customerJdbcRepository.findById(newCustomer.getCustomerId());
        assertThat(retrievedCustomer.isEmpty(), is(false));
        assertThat(retrievedCustomer.get(), samePropertyValuesAs(newCustomer));
    }

    @Test
    @DisplayName("전체 고객을 조회할 수 있다.")
    @Order(3)
    public void testFindAll() throws InterruptedException {
        var customers = customerJdbcRepository.findAll();
        assertThat(customers.isEmpty(), is(false));
    }

    @Test
    @DisplayName("이름으로 고객을 조회할 수 있다.")
    @Order(4)
    public void testFindByName() throws InterruptedException {
        var customers = customerJdbcRepository.findByName(newCustomer.getName());
        assertThat(customers.isEmpty(), is(false));

        var unknown = customerJdbcRepository.findByName("unknown-user");
        assertThat(unknown.isEmpty(), is(true));
    }

    @Test
    @DisplayName("이메일로 고객을 조회할 수 있다.")
    @Order(5)
    public void testFindByEmail() throws InterruptedException {
        var customers = customerJdbcRepository.findByEmail(newCustomer.getEmail());
        assertThat(customers.isEmpty(), is(false));

        var unknown = customerJdbcRepository.findByEmail("unknown-user@gmail.com");
        assertThat(unknown.isEmpty(), is(true));
    }

    @Test
    @DisplayName("고객을 수정할 수 있다.")
    @Order(6)
    public void testUpdate() {
        newCustomer.changeName("updated-user");
        customerJdbcRepository.update(newCustomer);

        final List<Customer> all = customerJdbcRepository.findAll();
        assertThat(all, hasSize(1));
        assertThat(all, everyItem(samePropertyValuesAs(newCustomer)));
    }
}