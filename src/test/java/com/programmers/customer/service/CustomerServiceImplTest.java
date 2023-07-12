package com.programmers.customer.service;

import com.programmers.customer.dto.CustomerRequestDto;
import com.programmers.customer.dto.CustomerResponseDto;
import com.programmers.customer.dto.CustomerUpdateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomerServiceImplTest {

    @Configuration
    @ComponentScan("com.programmers.customer")
    static class Config {
    }

    @Autowired
    private CustomerService customerService;

    @DisplayName("요청을 토대로 고객을 생성할 수 있다.")
    @Test
    void createCustomerByRequestTest() {
        CustomerRequestDto requestDto = new CustomerRequestDto(UUID.randomUUID(), "고객");

        CustomerResponseDto createdCustomer = customerService.create(requestDto);

        assertThat(requestDto.customerId()).isEqualTo(createdCustomer.customerId());
    }

    @DisplayName("고객의 이름을 수정할 수 있다.")
    @Test
    void updateCustomerNameTest() {
        UUID id = UUID.randomUUID();
        CustomerRequestDto requestDto = new CustomerRequestDto(id, "고객");
        CustomerResponseDto createdCustomer = customerService.create(requestDto);

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(id, "손님", LocalDateTime.now());
        CustomerResponseDto updatedCustomer = customerService.update(updateRequest);

        assertThat(createdCustomer.customerId()).isEqualTo(updatedCustomer.customerId());
        assertThat(createdCustomer.name()).isNotEqualTo(updatedCustomer.name());
    }

    @DisplayName("모든 고객을 조회할 수 있다.")
    @Test
    void findAllCustomersTest() {
        CustomerRequestDto requestDto = new CustomerRequestDto(UUID.randomUUID(), "고객");
        CustomerRequestDto requestDto2 = new CustomerRequestDto(UUID.randomUUID(), "손님");
        customerService.create(requestDto);
        customerService.create(requestDto2);

        List<CustomerResponseDto> customers = customerService.findCustomers();

        assertThat(customers.size()).isEqualTo(2);
    }
}
