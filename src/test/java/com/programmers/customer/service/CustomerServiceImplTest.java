package com.programmers.customer.service;

import com.programmers.customer.dto.CustomerRequestDto;
import com.programmers.customer.dto.CustomerResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;

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

    @Test
    void test() {
        CustomerRequestDto requestDto = new CustomerRequestDto(UUID.randomUUID(), "고객");

        CustomerResponseDto createdCustomer = customerService.create(requestDto);

        assertThat(requestDto.customerId()).isEqualTo(createdCustomer.customerId());
    }

    @Test
    void updateTest() {
        UUID id = UUID.randomUUID();
        CustomerRequestDto requestDto = new CustomerRequestDto(id, "고객");
        CustomerResponseDto createdCustomer = customerService.create(requestDto);

        CustomerRequestDto requestDto2 = new CustomerRequestDto(id, "손님");
        CustomerResponseDto updatedCustomer = customerService.update(requestDto2);

        assertThat(createdCustomer.customerId()).isEqualTo(updatedCustomer.customerId());
        assertThat(createdCustomer.name()).isNotEqualTo(updatedCustomer.name());
    }
}
