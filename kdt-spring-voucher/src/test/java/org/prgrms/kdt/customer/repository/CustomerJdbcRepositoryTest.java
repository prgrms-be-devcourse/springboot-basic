package org.prgrms.kdt.customer.repository;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.customer.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CustomerJdbcRepositoryTest {

    @Configuration
    @ComponentScan(
        basePackages = {"org.prgrms.kdt.customer"}
    )
    static class Config {
        @Autowired
        Environment environment;

        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                .url(environment.getProperty("mysql.url"))
                .username(environment.getProperty("mysql.username"))
                .password(environment.getProperty("mysql.password"))
                .type(HikariDataSource.class)
                .build();
        }

    }

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    DataSource dataSource;


    @BeforeEach
    void clearDatabase() {
        customerRepository.deleteAll();
    }

    @Test
    void testHikariConnectionPool() {
        assertThat(dataSource.getClass().getName()).isEqualTo("com.zaxxer.hikari.HikariDataSource");
    }

    @Test
    @DisplayName("전체 고객을 조회할 수 있다.")
    void testFindAll() {
        //given
        customerRepository.insert(new Customer(UUID.randomUUID(), "user1", "user1@gamil.com", LocalDateTime.now()));
        customerRepository.insert(new Customer(UUID.randomUUID(), "user2", "user2@gamil.com", LocalDateTime.now()));
        customerRepository.insert(new Customer(UUID.randomUUID(), "user3", "user3@gamil.com", LocalDateTime.now()));

        //when
        List<Customer> customers = customerRepository.findAll();

        //then
        assertThat(customers.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("이름으로 고객을 조회할 수 있다.")
    void testFindByNameO() {
        //given
        customerRepository.insert(new Customer(UUID.randomUUID(), "user1", "user1@gamil.com", LocalDateTime.now()));
        customerRepository.insert(new Customer(UUID.randomUUID(), "user2", "user2@gamil.com", LocalDateTime.now()));
        customerRepository.insert(new Customer(UUID.randomUUID(), "user3", "user3@gamil.com", LocalDateTime.now()));

        //when
        Optional<Customer> findCustomer = customerRepository.findByName("user1");

        //then
        assertThat(findCustomer.isEmpty()).isFalse();
        assertThat(findCustomer.get().getEmail()).isEqualTo("user1@gamil.com");
    }

    @Test
    @DisplayName("등록되지 않은 이름으로 고객 조회")
    void testFindByNameX() {
        //given
        customerRepository.insert(new Customer(UUID.randomUUID(), "user1", "user1@gamil.com", LocalDateTime.now()));
        customerRepository.insert(new Customer(UUID.randomUUID(), "user2", "user2@gamil.com", LocalDateTime.now()));
        customerRepository.insert(new Customer(UUID.randomUUID(), "user3", "user3@gamil.com", LocalDateTime.now()));

        //when
        Optional<Customer> findCustomer = customerRepository.findByName("xxx");

        //then
        assertThat(findCustomer.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("이메일로 고객을 조회할 수 있다.")
    void findByEmailO() {
        //given
        customerRepository.insert(new Customer(UUID.randomUUID(), "user1", "user1@gamil.com", LocalDateTime.now()));
        customerRepository.insert(new Customer(UUID.randomUUID(), "user2", "user2@gamil.com", LocalDateTime.now()));
        customerRepository.insert(new Customer(UUID.randomUUID(), "user3", "user3@gamil.com", LocalDateTime.now()));

        //when
        Optional<Customer> findCustomer = customerRepository.findByEmail("user1@gamil.com");

        //then
        assertThat(findCustomer.isEmpty()).isFalse();
    }

    @Test
    void testFindById(){
        //given
        UUID uuid = UUID.randomUUID();
        customerRepository.insert(new Customer(uuid, "user1", "user1@gamil.com", LocalDateTime.now()));

        //when
        Optional<Customer> findCustomer = customerRepository.findById(uuid);

        //then
        assertThat(findCustomer.get().getName()).isEqualTo("user1");
        assertThat(findCustomer.isEmpty()).isFalse();
    }

    @Test
    @DisplayName("고객을 추가할 수 있다.")
    void testInsert() {
        //given
        Customer newCustomer = new Customer(UUID.randomUUID(), "test-user", "test-user@gamil.com", LocalDateTime.now());
        UUID newCustomerId = newCustomer.getCustomerId();

        //when
        customerRepository.insert(newCustomer);

        //then
        Optional<Customer> findCustomer = customerRepository.findById(newCustomerId);
    }

    @Test
    @DisplayName("고객을 수정할 수 있다.")
    void testUpdate() {
        //given
        UUID uuid = UUID.randomUUID();
        customerRepository.insert(new Customer(uuid, "user1", "user1@gamil.com", LocalDateTime.now()));
        Customer newCustomer = new Customer(uuid, "jaeung", "jaeung@gmail.com", LocalDateTime.now());

        //when
        customerRepository.update(newCustomer);

        //then
        Optional<Customer> findCustomer = customerRepository.findById(uuid);
        assertThat(findCustomer.get().getName()).isEqualTo(newCustomer.getName());
        assertThat(findCustomer.get().getEmail()).isEqualTo(newCustomer.getEmail());
    }
}