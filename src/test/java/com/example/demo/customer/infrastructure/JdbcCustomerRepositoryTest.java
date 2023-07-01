package com.example.demo.customer.infrastructure;

import com.example.demo.customer.domain.Customer;
import com.example.demo.customer.domain.repostiory.CustomerRepository;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;

import java.time.LocalDateTime;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcCustomerRepositoryTest {

    @Configuration
    @ComponentScan(
            basePackages = {"com.example.demo.customer"}
    )
    static class config {

        @Bean
        public DataSource dataSource() {
            return new EmbeddedDatabaseBuilder()
                    .generateUniqueName(true)
                    .setType(H2)
                    .setScriptEncoding("UTF-8")
                    .ignoreFailedDrops(true)
                    .addScript("h2.sql")
                    .build();

//            return DataSourceBuilder.create()
//                    .url("jdbc:mysql://localhost/spring_basic")
//                    .username("root")
//                    .password("1234")
//                    .type(HikariDataSource.class)
//                    .build();
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

    @Autowired
    private DataSource dataSource;


    @Test
    @Order(1)
    @DisplayName("Hikari 커넥션을 사용하는지 확인하는 테스트")
    @Disabled
    void test_hikari() {
        assertThat(dataSource.getClass().getName(), is("com.zaxxer.hikari.HikariDataSource"));
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
    @Order(2)
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
        assertEquals(uuid, foundCustomer.get().getCustomerId());
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
