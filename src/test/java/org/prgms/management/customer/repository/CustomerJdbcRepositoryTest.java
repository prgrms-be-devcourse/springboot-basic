package org.prgms.management.customer.repository;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.prgms.management.customer.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@ActiveProfiles(value = "test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerJdbcRepositoryTest {
    @Autowired
    CustomerJdbcRepository customerJdbcRepository;

    List<Customer> newCustomers = new ArrayList<>();

    @BeforeAll
    void setup() {
        customerJdbcRepository.deleteAll();
        newCustomers.add(Customer.getCustomer(UUID.randomUUID(), "test-user1", LocalDateTime.now()));
        newCustomers.add(Customer.getCustomer(UUID.randomUUID(), "test-user2", LocalDateTime.now()));
        newCustomers.add(Customer.getCustomer(UUID.randomUUID(), "test-user3", LocalDateTime.now()));
        newCustomers.add(Customer.getCustomer(UUID.randomUUID(), "test-user4", LocalDateTime.now()));
    }

    @Test
    @Order(1)
    @DisplayName("고객을 추가 할 수 있다.")
    void insert() {
        newCustomers.forEach(v -> {
            var customer = customerJdbcRepository.insert(v);
            assertThat(customer == null, is(false));
            assertThat(customer.getCustomerId(), is(v.getCustomerId()));
        });
    }

    @Test
    @Order(2)
    @DisplayName("중복된 고객을 추가 할 수 없다.")
    void insertDuplicateCustomer() {
        newCustomers.forEach(v -> {
            var customer = customerJdbcRepository.insert(v);
            assertThat(customer == null, is(true));
        });
    }

    @Test
    @Order(3)
    @DisplayName("전체 고객을 조회 할 수 있다.")
    void findAll() {
        var customers = customerJdbcRepository.findAll();
        assertThat(customers.size(), is(newCustomers.size()));
    }

    @Test
    @Order(4)
    @DisplayName("아이디로 고객을 조회 할 수 있다.")
    void findById() {
        newCustomers.forEach(v -> {
            var customer = customerJdbcRepository.findById(
                    v.getCustomerId());
            assertThat(customer.isEmpty(), is(false));
        });
    }

    @Test
    @Order(5)
    @DisplayName("이름으로 고객을 조회 할 수 있다.")
    void findByName() {
        newCustomers.forEach(v -> {
            var customer = customerJdbcRepository.findByName(
                    v.getName());
            assertThat(customer.isEmpty(), is(false));
        });
    }

    @Test
    @Order(6)
    @DisplayName("고객을 수정 할 수 있다.")
    void update() {
        var name = "updated-user";
        newCustomers.get(0).changeName(name);
        var customer = customerJdbcRepository.update(
                newCustomers.get(0));

        assertThat(customer == null, is(false));
        assertThat(customer.getName(), is(name));
    }

    @Test
    @Order(7)
    @DisplayName("고객을 삭제 할 수 있다.")
    void delete() {
        var customer = customerJdbcRepository.delete(
                newCustomers.get(0));
        assertThat(customer == null, is(false));
    }

    @Test
    @Order(8)
    @DisplayName("모든 고객을 삭제 할 수 있다.")
    void deleteAll() {
        customerJdbcRepository.deleteAll();
        var customers = customerJdbcRepository.findAll();
        assertThat(customers.isEmpty(), is(true));
    }

    @Test
    @Order(9)
    @DisplayName("삭제한 고객은 검색 할 수 없다.")
    void findDeleted() {
        newCustomers.forEach(v -> {
            var customer = customerJdbcRepository.findById(v.getCustomerId());
            assertThat(customer.isEmpty(), is(true));
            customer = customerJdbcRepository.findByName(v.getName());
            assertThat(customer.isEmpty(), is(true));
        });
    }
}