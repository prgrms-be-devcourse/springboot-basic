package com.prgrms.management.customer.repository;

import com.prgrms.management.customer.domain.Customer;
import com.prgrms.management.customer.domain.CustomerRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@ActiveProfiles("test")
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcCustomerRepositoryTest {
    @Autowired
    CustomerRepository customerRepository;

    Customer customer, customerTwo;
    UUID randomId;

    @BeforeAll
    void setup() {
        customer = new Customer(new CustomerRequest("customerA", "prgrms@naver.com", "normal"));
        customerTwo = new Customer(new CustomerRequest("customerB", "13lxsdf@naver.com", "blacklist"));
        randomId = UUID.randomUUID();
    }

    @AfterAll
    void cleanUp() {
        customerRepository.deleteAll();
    }

    @Nested
    @Order(1)
    class Customer_정보_저장 {
        @Test
        void 성공_Customer_저장() {
            //when
            Customer saveCustomer = customerRepository.save(customer);
            //then
            Assertions.assertThat(customer).isEqualTo(saveCustomer);
        }
    }

    @Nested
    @Order(2)
    class Customer_검색 {
        @Test
        void 성공_ID로_Customer_검색() {
            //given
            UUID customerId = customer.getCustomerId();
            //when
            Optional<Customer> customerById = customerRepository.findById(customerId);
            //then
            Assertions.assertThat(customerById.get().getCustomerId()).isEqualTo(customerId);
        }

        @Test
        void 실패_ID로_Customer_검색() {
            //when
            Optional<Customer> customerById = customerRepository.findById(randomId);
            //then
            Assertions.assertThat(customerById.isEmpty()).isEqualTo(true);
        }

        @Test
        void 성공_EMAIL로_Customer_검색() {
            //given
            String email = customer.getEmail();
            //when
            Optional<Customer> customerById = customerRepository.findByEmail(email);
            //then
            Assertions.assertThat(customerById.get().getEmail()).isEqualTo(email);
        }

        @Test
        void 실패_EMAIL로_Customer_검색() {
            //given
            String email = "fail@naver.com";
            //when
            Optional<Customer> customerById = customerRepository.findByEmail(email);
            //then
            Assertions.assertThat(customerById.isEmpty()).isEqualTo(true);
        }
    }

    @Nested
    @Order(3)
    class 여러명의_Customers_검색 {
        @Test
        void 성공_여러명의_Customers_검색() {
            //given
            customerRepository.save(customerTwo);
            //when
            List<Customer> customers = customerRepository.findAll();
            //then
            Assertions.assertThat(customers.size()).isEqualTo(2);
        }

        @Test
        void 성공_여러명의_BlackList_검색() {
            //when
            List<Customer> customers = customerRepository.findBlackList();
            //then
            Assertions.assertThat(customers.size()).isEqualTo(1);
        }
    }

    @Nested
    @Order(4)
    class 이름_업데이트_Customer {
        @Test
        void 성공_이름_업데이트_Customer() {
            //given
            String name = "hello";
            customer.setName(name);
            //when
            customerRepository.updateName(customer);
            //then
            Assertions.assertThat(customer.getName()).isEqualTo(name);
        }
    }

    @Nested
    @Order(5)
    class 삭제_Customer {
        @Test
        void 성공_삭제_Customer() {
            //given
            UUID customerId = customer.getCustomerId();
            //when
            customerRepository.deleteById(customerId);
            //then
            Assertions.assertThat(customerRepository.findById(customerId).isEmpty()).isEqualTo(true);
        }

        @Test
        void 실패_삭제_Customer() {
            //when
            customerRepository.deleteById(randomId);
            //then
            Assertions.assertThat(customerRepository.findById(randomId).isEmpty()).isEqualTo(true);
        }
    }
}