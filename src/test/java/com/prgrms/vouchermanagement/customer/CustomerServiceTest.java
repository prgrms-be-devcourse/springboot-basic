package com.prgrms.vouchermanagement.customer;

import org.junit.jupiter.api.AfterEach;
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

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringJUnitConfig
class CustomerServiceTest {

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
        CustomerNamedJdbcRepository customerRepository(NamedParameterJdbcTemplate jdbcTemplate) {
            return new CustomerNamedJdbcRepository(jdbcTemplate);
        }

        @Bean
        CustomerService customerService(CustomerNamedJdbcRepository customerRepository) {
            return new CustomerService(customerRepository);
        }
    }

    @Autowired
    CustomerNamedJdbcRepository customerRepository;

    @Autowired
    CustomerService customerService;

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    @AfterEach
    void afterEach() {
        jdbcTemplate.update("DELETE FROM customer", Collections.emptyMap());
    }

    @Test
    @DisplayName("name과 email을 입력 받아 Customer를 생성한다.")
    void addCustomerTest() {
        // given
        String name = "aaa";
        String email = "aaa@gmail.com";

        // when
        customerService.addCustomer(name, email);

        // then
        Customer findCustomer = customerRepository.findByEmail(email).get();
        assertThat(findCustomer.getName()).isEqualTo(name);
        assertThat(findCustomer.getEmail()).isEqualTo(email);
    }

    @Test
    @DisplayName("중복된 email로 Customer를 추가하면 예외가 발생한다.")
    void addCustomerDuplicateEmailTest() {
        // given
        String duplicateEmail = "duplicate@gmail.com";
        customerRepository.save(Customer.of("aaa", duplicateEmail));

        // then
        assertThatThrownBy(() -> {
            // when
            customerService.addCustomer("bbb", duplicateEmail);
        })
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("중복된 email입니다");
    }

    @Test
    @DisplayName("email을 입력받아 등록된 Customer인지 확인한다.")
    void isRegisteredCustomerByEmailTest() {
        // given
        String email = "aaa@gmail.com";
        customerRepository.save(Customer.of("aaa", email));

        // when
        boolean registeredCustomer = customerService.isRegisteredCustomer(email);

        // then
        assertThat(registeredCustomer).isTrue();
    }

    @Test
    @DisplayName("등록되지 않은 eamil로 isRegisteredCustomer를 호출하면 false가 반환된다.")
    void isRegisteredCustomerNotExistsEmailTest() {
        // given
        String email = "aaa@gmail.com";
        customerRepository.save(Customer.of("aaa", email));

        // when
        boolean registeredCustomer = customerService.isRegisteredCustomer("notExists@gmail.com");

        // then
        assertThat(registeredCustomer).isFalse();
    }

    @Test
    @DisplayName("Id를 입력받아 등록된 Customer인지 확인한다.")
    void isRegisteredCustomerByIdTest() {
        // given
        Long customerId = customerRepository.save(Customer.of("aaa", "aaa@gmail.com"));

        // when
        boolean registeredCustomer = customerService.isRegisteredCustomer(customerId);

        // then
        assertThat(registeredCustomer).isTrue();
    }

    @Test
    @DisplayName("등록되지 않은 Id로 isRegisteredCustomer를 호출하면 false가 반환된다.")
    void isRegisteredCustomerNotExistsIdTest() {
        // given
        Long notExistsCustomerId = -1L;
        customerRepository.save(Customer.of("aaa", "aaa@gmail.com"));

        // when
        boolean registeredCustomer = customerService.isRegisteredCustomer(notExistsCustomerId);

        // then
        assertThat(registeredCustomer).isFalse();
    }
}