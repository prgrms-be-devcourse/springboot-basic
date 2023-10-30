package com.programmers.vouchermanagement.service;

import com.programmers.vouchermanagement.domain.customer.Customer;
import com.programmers.vouchermanagement.dto.customer.CreateCustomerRequestDto;
import com.programmers.vouchermanagement.dto.customer.GetCustomersRequestDto;
import com.programmers.vouchermanagement.repository.customer.CustomerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class CustomerServiceTest {

    private CustomerRepository customerRepository;
    private CustomerService customerService;

    CustomerServiceTest() {
        this.customerRepository = Mockito.mock(CustomerRepository.class);
        this.customerService = new CustomerService(customerRepository);
    }

    @Test
    @DisplayName("고객을 생성할 수 있다.")
    void creatCustomer() {
        // given
        Customer newCustomer = new Customer("test@email.com", false);
        CreateCustomerRequestDto request = CreateCustomerRequestDto.builder()
                .email(newCustomer.getEmail())
                .build();
        given(customerRepository.findByEmail(newCustomer.getEmail())).willReturn(Optional.empty());

        // when
        customerService.createCustomer(request);

        // then
        verify(customerRepository).save(any(Customer.class));
    }

    @Test
    @DisplayName("이메일이 중복되는 고객을 생성할 수 없다.")
    void creatCustomer_duplicatedEmail_fail() {
        // given
        Customer newCustomer1 = new Customer("test@email.com", false);
        Customer newCustomer2 = new Customer("test@email.com", false);

        CreateCustomerRequestDto request = CreateCustomerRequestDto.builder()
                .email(newCustomer1.getEmail())
                .build();

        given(customerRepository.findByEmail(newCustomer2.getEmail())).willReturn(Optional.of(newCustomer1));

        // when & then
        assertThatThrownBy(() -> customerService.createCustomer(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Already exist customer");
    }

    @Test
    @DisplayName("고객 목록을 조회할 수 있다.")
    void getCustomers() {
        // given
        Customer newCustomer1 = new Customer("test1@email.com");
        Customer newCustomer2 = new Customer("test2email.com");

        List<Customer> mockCustomers = Arrays.asList(newCustomer1, newCustomer2);
        given(customerRepository.findAll(any())).willReturn(mockCustomers);

        // when
        List<Customer> customers = customerService.getCustomers(new GetCustomersRequestDto());

        // then
        assertThat(customers).hasSize(2);
        assertThat(customers).extracting(Customer::getEmail)
                .containsExactlyInAnyOrder(newCustomer1.getEmail(), newCustomer2.getEmail());
        assertThat(customers).extracting(Customer::isBlacklisted)
                .containsExactlyInAnyOrder(newCustomer1.isBlacklisted(), newCustomer2.isBlacklisted());
    }
}
