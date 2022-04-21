package com.prgrms.vouchermanagement.customer;

import com.prgrms.vouchermanagement.util.FilePathProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig
class CustomerNamedJdbcRepositoryTest {

    @Configuration
    @ComponentScan
    @EnableConfigurationProperties(FilePathProperties.class)
    static class TestConfig {

        @Bean
        public DataSource dataSource() {
            return new EmbeddedDatabaseBuilder()
                    .generateUniqueName(true)
                    .setType(EmbeddedDatabaseType.H2)
                    .addScript("customer_schema.sql")
                    .setScriptEncoding("UTF-8")
                    .build();
        }

        @Bean
        NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
            return new NamedParameterJdbcTemplate(dataSource);
        }
    }

    @Autowired
    CustomerNamedJdbcRepository customerRepository;

    @BeforeEach
    void init() {
        customerRepository.clear();
    }

    @Test
    @DisplayName("Customer 를 저장한다.")
    void customerSaveTest() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "aaa", "aaa@gamil.com", LocalDateTime.now());

        //when
        customerRepository.save(customer);

        //then
        Optional<Customer> optionalCustomer = customerRepository.findById(customer.getCustomerId());
        assertThat(optionalCustomer).isNotEmpty();

        Customer findCustomer = optionalCustomer.get();
        assertThat(findCustomer.getCustomerId()).isEqualByComparingTo(customer.getCustomerId());
        assertThat(findCustomer.getEmail()).isEqualTo(customer.getEmail());
        assertThat(findCustomer.getName()).isEqualTo(customer.getName());
    }

    @Test
    @DisplayName("customerId로 Customer를 조회한다.")
    void findByIdTest() {
        // given
        Customer customer = new Customer(UUID.randomUUID(), "aaa", "aaa@gmail.com", LocalDateTime.now());
        customerRepository.save(customer);

        // when
        Optional<Customer> optionalCustomer = customerRepository.findById(customer.getCustomerId());

        // then
        assertThat(optionalCustomer).isNotEmpty();
        Customer findCustomer = optionalCustomer.get();
        assertThat(findCustomer.getCustomerId()).isEqualByComparingTo(customer.getCustomerId());
        assertThat(findCustomer.getName()).isEqualTo(customer.getName());
        assertThat(findCustomer.getEmail()).isEqualTo(customer.getEmail());
    }

    @Test
    @DisplayName("존재하지 않는 customerId로 조회하면 Optional.empty()가 반환된다.")
    void findByNotExistsIdTest() {
        // given
        Customer customer = new Customer(UUID.randomUUID(), "aaa", "aaa@gmail.com", LocalDateTime.now());
        customerRepository.save(customer);

        // when
        Optional<Customer> findCustomer = customerRepository.findById(UUID.randomUUID());

        // then
        assertThat(findCustomer).isEmpty();
    }

    @Test
    @DisplayName("name으로 Customer를 조회한다.")
    void findByNameTest() {
        // given
        String targetName = "aaa";
        Customer customer1 = new Customer(UUID.randomUUID(), targetName, "aa@gmail.com", LocalDateTime.now());
        Customer customer2 = new Customer(UUID.randomUUID(), targetName, "bb@gmail.com", LocalDateTime.now());
        Customer customer3 = new Customer(UUID.randomUUID(), targetName, "cc@gmail.com", LocalDateTime.now());
        Customer customer4 = new Customer(UUID.randomUUID(), "ccc", "dd@gmail.com", LocalDateTime.now());
        Customer customer5 = new Customer(UUID.randomUUID(), "ddd", "ee@gmail.com", LocalDateTime.now());

        customerRepository.save(customer1);
        customerRepository.save(customer2);
        customerRepository.save(customer3);
        customerRepository.save(customer4);
        customerRepository.save(customer5);

        // when
        List<Customer> customers = customerRepository.findByName(targetName);

        // then
        assertThat(customers.size()).isEqualTo(3);
        assertThat(customers).usingRecursiveFieldByFieldElementComparator().contains(customer1, customer2, customer3);
    }

    @Test
    @DisplayName("존재하지 않는 name으로 Customer를 조회하면 빈 리스트가 반환된다.")
    void findByNotExistsNameTest() {
        // given
        Customer customer1 = new Customer(UUID.randomUUID(), "aaa", "aa@gmail.com", LocalDateTime.now());
        Customer customer2 = new Customer(UUID.randomUUID(), "bbb", "bb@gmail.com", LocalDateTime.now());
        Customer customer3 = new Customer(UUID.randomUUID(), "ccc", "cc@gmail.com", LocalDateTime.now());

        customerRepository.save(customer1);
        customerRepository.save(customer2);
        customerRepository.save(customer3);

        // when
        List<Customer> customers = customerRepository.findByName("ddd");

        // then
        assertThat(customers.size()).isZero();
    }

    @Test
    @DisplayName("eamil로 Customer를 조회한다.")
    void findByEmailTest() {
        // given
        Customer customer = new Customer(UUID.randomUUID(), "aaa", "aaa@gmail.com", LocalDateTime.now());
        customerRepository.save(customer);

        // when
        Optional<Customer> optionalCustomer = customerRepository.findByEmail(customer.getEmail());

        // then
        assertThat(optionalCustomer).isNotEmpty();
        Customer findCustomer = optionalCustomer.get();
        assertThat(findCustomer.getCustomerId()).isEqualByComparingTo(customer.getCustomerId());
        assertThat(findCustomer.getName()).isEqualTo(customer.getName());
        assertThat(findCustomer.getEmail()).isEqualTo(customer.getEmail());
    }
    
    @Test
    @DisplayName("등록되지 않은 이메일로 조회하면 Optional.empty()가 반환된다.")
    void findByNotExistsEmailTest() {
        // given
        Customer customer = new Customer(UUID.randomUUID(), "aaa", "aaa@gmail.com", LocalDateTime.now());
        customerRepository.save(customer);

        // when
        Optional<Customer> findCustomer = customerRepository.findByEmail("bbb@gmail.com");

        // then
        assertThat(findCustomer).isEmpty();
    }

    @Test
    @DisplayName("Customer를 update한다.")
    void updateTest() {
        // given
        Customer customer = new Customer(UUID.randomUUID(), "aaa", "aaa@gmail.com", LocalDateTime.now());
        customerRepository.save(customer);

        // when
        Customer updateCustomer = new Customer(customer.getCustomerId(), "bbb", "bbb@gmail.com", customer.getCreatedAt());
        customerRepository.update(updateCustomer);

        // then
        Customer findCustomer = customerRepository.findById(customer.getCustomerId()).get();
        assertThat(findCustomer).usingRecursiveComparison().isEqualTo(updateCustomer);
    }

    @Test
    @DisplayName("모든 Customer를 조회한다.")
    void findAll() {
        // given
        Customer customer1 = new Customer(UUID.randomUUID(), "aaa", "aaa@gmail.com", LocalDateTime.now());
        Customer customer2 = new Customer(UUID.randomUUID(), "bbb", "bbb@gmail.com", LocalDateTime.now());
        Customer customer3 = new Customer(UUID.randomUUID(), "ccc", "ccc@gmail.com", LocalDateTime.now());

        customerRepository.save(customer1);
        customerRepository.save(customer2);
        customerRepository.save(customer3);

        // when
        List<Customer> allCustomers = customerRepository.findAll();

        // then
        assertThat(allCustomers.size()).isEqualTo(3);
        assertThat(allCustomers).usingRecursiveFieldByFieldElementComparator().contains(customer1, customer2, customer3);
    }
}