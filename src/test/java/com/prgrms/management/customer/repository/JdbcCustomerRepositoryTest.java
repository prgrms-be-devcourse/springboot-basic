package com.prgrms.management.customer.repository;

import com.prgrms.management.customer.domain.Customer;
import com.prgrms.management.customer.domain.CustomerRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@ActiveProfiles("test")
class JdbcCustomerRepositoryTest {
    @Autowired
    CustomerRepository customerRepository;

    Customer customer = new Customer(new CustomerRequest("customerA", "prgrms@naver.com", "normal"));
    Customer customerTwo = new Customer(new CustomerRequest("customerB", "13lxsdf@naver.com", "blacklist"));
    Customer saveCustomer;
    UUID randomId = UUID.randomUUID();

    @BeforeEach
    void setup() {
        saveCustomer = customerRepository.save(customer);
    }

    @AfterEach
    void cleanUp() {
        customerRepository.deleteAll();
    }

    @Test
    void 성공_Customer_저장() {
        //then
        Assertions.assertThat(customer).isEqualTo(saveCustomer);
    }

    @Nested
    class Customer_검색 {
        @Test
        void ID로_Customer_검색() {
            //given
            UUID customerId = customer.getCustomerId();
            //when
            Optional<Customer> customerById = customerRepository.findById(customerId);
            //then
            Assertions.assertThat(customerById.get().getCustomerId()).isEqualTo(customerId);
        }

        @Test
        void EMAIL로_Customer_검색() {
            //given
            String email = customer.getEmail();
            //when
            Optional<Customer> customerById = customerRepository.findByEmail(email);
            //then
            Assertions.assertThat(customerById.get().getEmail()).isEqualTo(email);
        }

        @Test
        void ID로_Customer_검색_실패() {
            //when
            Optional<Customer> customerById = customerRepository.findById(randomId);
            //then
            Assertions.assertThat(customerById.isEmpty()).isEqualTo(true);
        }

        @Test
        void EMAIL로_Customer_검색_실패() {
            //given
            String email = "fail@naver.com";
            //when
            Optional<Customer> customerById = customerRepository.findByEmail(email);
            //then
            Assertions.assertThat(customerById.isEmpty()).isEqualTo(true);
        }
    }

    @Test
    void 여러명의_Customers_검색() {
        //when
        List<Customer> customers = customerRepository.findAll();
        //then
        Assertions.assertThat(customers.size()).isEqualTo(1);
    }

    @Test
    void Customer_이름_업데이트() {
        //given
        String name = "hello";
        customer.setName(name);
        //when
        customerRepository.updateName(customer);
        //then
        Assertions.assertThat(customer.getName()).isEqualTo(name);
    }

    @Nested
    class Customer_삭제 {
        @Test
        void Customer_삭제() {
            //given
            UUID customerId = customer.getCustomerId();
            //when
            customerRepository.deleteById(customerId);
            //then
            Assertions.assertThat(customerRepository.findById(customerId).isEmpty()).isEqualTo(true);
        }

        @Test
        void Customer_삭제_실패() {
            //when
            customerRepository.deleteById(randomId);
            //then
            Assertions.assertThat(customerRepository.findById(randomId).isEmpty()).isEqualTo(true);
        }
    }
}