package com.example.demo.customer.infrastructure;

import com.example.demo.customer.domain.Customer;
import com.example.demo.customer.domain.repostiory.CustomerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringJUnitConfig
public class TestContainerJdbcRCustomerRepositoryTest {

    @Container
    public static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:8")
            .withDatabaseName("spring_basic")
            .withUsername("test")
            .withPassword("test1234")
            .withInitScript("mysql.sql");

    @Configuration
    @ComponentScan(
            basePackages = {"com.example.demo.customer"}
    )
    static class config {

        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .url(mySQLContainer.getJdbcUrl())
                    .username(mySQLContainer.getUsername())
                    .password(mySQLContainer.getPassword())
                    .driverClassName(mySQLContainer.getDriverClassName())
                    .build();
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }

        @Bean
        public Set<String> blacklist() {
            return new HashSet<>();
        }
    }

    @Autowired
    private CustomerRepository customerRepository;

    @AfterEach
    void tearDown() {
        customerRepository.deleteAll();
    }

    @Test
    @DisplayName("데이터 삽입 테스트")
    void insert() {
        // given
        UUID uuid = UUID.randomUUID();
        Customer customer = new Customer(uuid, "name", "email@test.com", LocalDateTime.now(), LocalDateTime.now());

        // when
        Customer insertedCustomer = customerRepository.insert(customer);

        // then
        assertNotNull(insertedCustomer);
        assertEquals(customer, insertedCustomer);
    }

    @Test
    @DisplayName("데이터 업데이트 테스트")
    void update() {
        // given
        UUID uuid = UUID.randomUUID();
        Customer customer = new Customer(uuid, "name", "email@test.com", LocalDateTime.now(), LocalDateTime.now());
        customerRepository.insert(customer);

        Customer updatedCustomer = new Customer(uuid, "updated name", "email@test.com", LocalDateTime.now(), LocalDateTime.now());

        // when
        Customer resultCustomer = customerRepository.update(updatedCustomer);

        // then
        assertNotNull(resultCustomer);
        assertEquals("updated name", resultCustomer.getName());
    }

    @Test
    @DisplayName("전체 조회 테스트")
    void findAll() {
        // given
        Customer customer1 = new Customer(UUID.randomUUID(), "name1", "email1@test.com", LocalDateTime.now(), LocalDateTime.now());
        Customer customer2 = new Customer(UUID.randomUUID(), "name2", "email2@test.com", LocalDateTime.now(), LocalDateTime.now());

        customerRepository.insert(customer1);
        customerRepository.insert(customer2);

        // when
        List<Customer> customers = customerRepository.findAll();

        // then
        assertNotNull(customers);
        assertEquals(2, customers.size());
    }

    @Test
    @DisplayName("id로 조회 테스트")
    void findById() {
        // given
        UUID uuid = UUID.randomUUID();
        Customer customer = new Customer(uuid, "name", "email@test.com", LocalDateTime.now(), LocalDateTime.now());
        customerRepository.insert(customer);

        // when
        Optional<Customer> foundCustomer = customerRepository.findById(uuid);

        // then
        assertTrue(foundCustomer.isPresent());
        assertEquals(customer.getCustomerId(), foundCustomer.get().getCustomerId());
    }

    @Test
    @DisplayName("이름으로 조회 테스트")
    void findByName() {
        // given
        String name = "name";
        Customer customer = new Customer(UUID.randomUUID(), name, "email@test.com", LocalDateTime.now(), LocalDateTime.now());
        customerRepository.insert(customer);

        // when
        Optional<Customer> foundCustomer = customerRepository.findByName(name);

        // then
        assertTrue(foundCustomer.isPresent());
        assertEquals(name, foundCustomer.get().getName());
    }

    @Test
    @DisplayName("email로 조회 테스트")
    void findByEmail() {
        // given
        String email = "email@test.com";
        Customer customer = new Customer(UUID.randomUUID(), "name", email, LocalDateTime.now(), LocalDateTime.now());
        customerRepository.insert(customer);

        // when
        Optional<Customer> foundCustomer = customerRepository.findByEmail(email);

        // then
        assertTrue(foundCustomer.isPresent());
        assertEquals(email, foundCustomer.get().getEmail());
    }

    @Test
    @DisplayName("데이터 전체 삭제 테스트")
    void deleteAll() {
        // given
        Customer customer1 = new Customer(UUID.randomUUID(), "name1", "email1@test.com", LocalDateTime.now(), LocalDateTime.now());
        Customer customer2 = new Customer(UUID.randomUUID(), "name2", "email2@test.com", LocalDateTime.now(), LocalDateTime.now());

        customerRepository.insert(customer1);
        customerRepository.insert(customer2);

        // when
        customerRepository.deleteAll();
        List<Customer> customers = customerRepository.findAll();

        // then
        assertTrue(customers.isEmpty());
    }

}
