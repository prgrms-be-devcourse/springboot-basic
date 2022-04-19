package com.prgrms.management.customer.service;

import com.prgrms.management.customer.domain.Customer;
import com.prgrms.management.customer.domain.CustomerRequest;
import com.prgrms.management.customer.repository.CustomerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.UUID;

@SpringBootTest
@ActiveProfiles("test")
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerServiceTest {

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CustomerService customerService;

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

    @Test
    @Order(1)
    void 성공_저장_Customer() {
        //given
        //when
        Customer createCustomer = customerService.createCustomer(customer);
        //then
        Assertions.assertThat(createCustomer.getCustomerId()).isEqualTo(customer.getCustomerId());
    }

    @Nested
    @Order(2)
    class 모든_Customer_조회 {
        @Test
        void 성공_모든_Customer_조회() {
            //given
            customerRepository.save(customerTwo);
            //when
            List<Customer> customers = customerService.findAll();
            //then
            Assertions.assertThat(customers.size()).isEqualTo(2);
        }

        @Test
        void 성공_모든_BLACKLIST_Customer_조회() {
            //when
            List<Customer> customers = customerService.findBlackList();
            //then
            Assertions.assertThat(customers.size()).isEqualTo(1);
        }

    }

    @Nested
    @Order(3)
    class 단일_Customer_조회 {
        @Test
        void 성공_ID로_Customer_조회() {
            //given
            //when
            Customer customerById = customerService.findById(customer.getCustomerId());
            //then
            Assertions.assertThat(customerById.getCustomerId()).isEqualTo(customer.getCustomerId());
        }

        @Test
        void 성공_EMAIL로_Customer_조회() {
            //given
            //when
            Customer customerByEmail = customerService.findByEmail(customer.getEmail());
            //then
            Assertions.assertThat(customerByEmail.getCustomerId()).isEqualTo(customer.getCustomerId());
        }
    }

    @Test
    @Order(4)
    void 성공_ID로_Customer_업데이트() {
        //given
        String updateName = "update";
        //when
        customerService.updateCustomer(customer.getCustomerId(),updateName);
        //then
        Assertions.assertThat(customerService.findById(customer.getCustomerId()).getName()).isEqualTo(updateName);
    }
}