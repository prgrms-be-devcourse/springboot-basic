package com.prgrms.vouchermanagement.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.text.CollationElementIterator;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig
class CustomerNamedJdbcRepositoryTest {

    @Configuration
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

        @Bean
        CustomerNamedJdbcRepository customerNamedJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
            return new CustomerNamedJdbcRepository(jdbcTemplate);
        }
    }

    @Autowired
    CustomerNamedJdbcRepository customerRepository;

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    @BeforeEach
    void init() {
        jdbcTemplate.update("DELETE FROM customer", Collections.emptyMap());
    }

    @Test
    @DisplayName("Customer 를 저장한다.")
    void customerSaveTest() {
        //given
        Customer customer = Customer.of("aaa", "aaa@gamil.com");

        //when
        Long customerId = customerRepository.save(customer);

        //then
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        assertThat(optionalCustomer).isNotEmpty();

        Customer findCustomer = optionalCustomer.get();
        assertThat(customerId).isEqualTo(findCustomer.getCustomerId());
        assertThat(findCustomer.getEmail()).isEqualTo(customer.getEmail());
        assertThat(findCustomer.getName()).isEqualTo(customer.getName());
    }

    @Test
    @DisplayName("customerId로 Customer를 조회한다.")
    void findByIdTest() {
        // given
        Customer customer = Customer.of("aaa", "aaa@gmail.com");
        Long customerId = customerRepository.save(customer);

        // when
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);

        // then
        assertThat(optionalCustomer).isNotEmpty();
        Customer findCustomer = optionalCustomer.get();
        assertThat(customerId).isEqualTo(findCustomer.getCustomerId());
        assertThat(findCustomer.getName()).isEqualTo(customer.getName());
        assertThat(findCustomer.getEmail()).isEqualTo(customer.getEmail());
    }

    @Test
    @DisplayName("존재하지 않는 customerId로 조회하면 Optional.empty()가 반환된다.")
    void findByNotExistsIdTest() {
        // given
        Customer customer = Customer.of("aaa", "aaa@gmail.com");
        Long customerId = customerRepository.save(customer);
        Long wrongId = -1L;

        // when
        Optional<Customer> findCustomer = customerRepository.findById(wrongId);

        // then
        assertThat(findCustomer).isEmpty();
    }

    @Test
    @DisplayName("name으로 Customer를 조회한다.")
    void findByNameTest() {
        // given
        String targetName = "aaa";
        Customer customer1 = Customer.of(targetName, "aa@gmail.com");
        Customer customer2 = Customer.of(targetName, "bb@gmail.com");
        Customer customer3 = Customer.of(targetName, "cc@gmail.com");
        Customer customer4 = Customer.of("ccc", "dd@gmail.com");
        Customer customer5 = Customer.of("ddd", "ee@gmail.com");

        customerRepository.save(customer1);
        customerRepository.save(customer2);
        customerRepository.save(customer3);
        customerRepository.save(customer4);
        customerRepository.save(customer5);

        // when
        List<Customer> customers = customerRepository.findByName(targetName);

        // then
        assertThat(customers.size()).isEqualTo(3);
        assertThat(customers).usingRecursiveFieldByFieldElementComparatorIgnoringFields("customerId").contains(customer1, customer2, customer3);
    }

    @Test
    @DisplayName("존재하지 않는 name으로 Customer를 조회하면 빈 리스트가 반환된다.")
    void findByNotExistsNameTest() {
        // given
        Customer customer1 = Customer.of("aaa", "aa@gmail.com");
        Customer customer2 = Customer.of("bbb", "bb@gmail.com");
        Customer customer3 = Customer.of("ccc", "cc@gmail.com");

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
        Customer customer = Customer.of("aaa", "aaa@gmail.com");
        customerRepository.save(customer);

        // when
        Optional<Customer> optionalCustomer = customerRepository.findByEmail(customer.getEmail());

        // then
        assertThat(optionalCustomer).isNotEmpty();
        Customer findCustomer = optionalCustomer.get();
        assertThat(findCustomer.getName()).isEqualTo(customer.getName());
        assertThat(findCustomer.getEmail()).isEqualTo(customer.getEmail());
    }

    @Test
    @DisplayName("등록되지 않은 이메일로 조회하면 Optional.empty()가 반환된다.")
    void findByNotExistsEmailTest() {
        // given
        Customer customer = Customer.of("aaa", "aaa@gmail.com");
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
        Customer customer = Customer.of("aaa", "aaa@gmail.com");
        Long customerId = customerRepository.save(customer);

        // when
        Customer updateCustomer = Customer.of(customerId, "bbb", "bbb@gmail.com", null, customer.getCreatedAt());
        customerRepository.update(updateCustomer);

        // then
        Customer findCustomer = customerRepository.findById(customerId).get();
        assertThat(findCustomer).usingRecursiveComparison().isEqualTo(updateCustomer);
    }

    @Test
    @DisplayName("모든 Customer를 조회한다.")
    void findAll() {
        // given
        Customer customer1 = Customer.of("aaa", "aaa@gmail.com");
        Customer customer2 = Customer.of("bbb", "bbb@gmail.com");
        Customer customer3 = Customer.of("ccc", "ccc@gmail.com");

        customerRepository.save(customer1);
        customerRepository.save(customer2);
        customerRepository.save(customer3);

        // when
        List<Customer> allCustomers = customerRepository.findAll();

        // then
        assertThat(allCustomers.size()).isEqualTo(3);
        assertThat(allCustomers).usingRecursiveFieldByFieldElementComparatorIgnoringFields("customerId").contains(customer1, customer2, customer3);
    }
}