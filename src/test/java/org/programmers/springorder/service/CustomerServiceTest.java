package org.programmers.springorder.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.springorder.dto.customer.CustomerRequestDto;
import org.programmers.springorder.dto.customer.CustomerResponseDto;
import org.programmers.springorder.model.customer.Customer;
import org.programmers.springorder.model.customer.CustomerType;
import org.programmers.springorder.repository.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = "command.line.runner.enabled=false")
class CustomerServiceTest {

    @Autowired
    CustomerService customerService;

    @Autowired
    CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        customerRepository.deleteAll();
    }

    @Test
    @DisplayName("회원 저장에 성공한다.")
    void createVoucher() {
        // given
        CustomerRequestDto request1 = new CustomerRequestDto("홍길동");
        CustomerRequestDto request2 = new CustomerRequestDto("세종대왕");

        // when
        customerService.createCustomer(request1);
        customerService.createCustomer(request2);

        // then
        List<Customer> customers = customerRepository.findAll();
        assertThat(customers).hasSize(2);
    }

    @Test
    @DisplayName("블랙리스트 조회에 성공한다.")
    void getBlackList() {
        // given
        Customer customer1 = Customer.toCustomer(UUID.randomUUID(), "홍길동", CustomerType.NORMAL);
        Customer customer2 = Customer.toCustomer(UUID.randomUUID(), "세종대왕", CustomerType.BLACK);

        customerRepository.save(customer1);
        customerRepository.save(customer2);

        // when
        List<CustomerResponseDto> blacklist = customerService.getBlackList();

        // then
        assertThat(blacklist).hasSize(1);
    }
}