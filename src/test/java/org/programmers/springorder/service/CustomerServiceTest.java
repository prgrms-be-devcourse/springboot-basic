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
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
    @DisplayName("전체 회원 조회에 성공한다.")
    void getAllCustomer() {
        // given
        CustomerRequestDto request1 = new CustomerRequestDto("홍길동");
        CustomerRequestDto request2 = new CustomerRequestDto("세종대왕");

        customerService.createCustomer(request1);
        customerService.createCustomer(request2);

        // when
        List<CustomerResponseDto> customerList = customerService.getAllCustomer();

        // then
        assertThat(customerList).hasSize(2);
    }

    @Test
    @DisplayName("블랙리스트 조회에 성공한다.")
    void getBlackList() {
        // given
        Customer customer1 = Customer.toCustomer(UUID.randomUUID(), "홍길동", CustomerType.NORMAL);
        Customer customer2 = Customer.toCustomer(UUID.randomUUID(), "세종대왕", CustomerType.BLACK);

        customerRepository.insert(customer1);
        customerRepository.insert(customer2);

        // when
        List<CustomerResponseDto> blacklist = customerService.getBlackList();

        // then
        assertThat(blacklist).hasSize(1);
    }

    @Test
    @DisplayName("고객 ID가 존재하지 않으면, 에러 메시지를 띄운다.")
    void findByIdFail() {
        // given
        UUID findCustomerId = UUID.randomUUID();

        // then
        assertThatThrownBy(() -> customerService.findById(findCustomerId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("고객 ID가 존재하지 않습니다.");
    }

    @Test
    @DisplayName("고객 삭제에 성공한다.")
    void deleteCustomer() {
        // given
        CustomerRequestDto request = new CustomerRequestDto("홍길동");
        Customer customer = customerService.createCustomer(request);
        UUID savedCustomerId = customer.getCustomerId();

        // when
        customerService.deleteCustomer(savedCustomerId);
        Optional<Customer> findCustomer = customerRepository.findById(savedCustomerId);

        // then
        assertThat(findCustomer).isNotPresent();
    }

    @Test
    @DisplayName("고객 ID가 존재하지 않으면, 삭제에 실패하고 에러 메시지를 띄운다.")
    void deleteCustomerFail() {
        // given
        UUID findCustomerId = UUID.randomUUID();

        // then
        assertThatThrownBy(() -> customerService.deleteCustomer(findCustomerId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("고객 ID가 존재하지 않습니다.");
    }
}