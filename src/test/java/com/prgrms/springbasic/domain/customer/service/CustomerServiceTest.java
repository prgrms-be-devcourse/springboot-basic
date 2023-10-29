package com.prgrms.springbasic.domain.customer.service;

import com.prgrms.springbasic.domain.customer.dto.CreateCustomerRequest;
import com.prgrms.springbasic.domain.customer.dto.CustomerResponse;
import com.prgrms.springbasic.domain.customer.entity.Customer;
import com.prgrms.springbasic.domain.customer.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;
    @Mock
    private CustomerRepository customerRepository;

    @BeforeEach
    void setup() {
        customerService = new CustomerService(customerRepository);
    }

    @Test
    @DisplayName("고객 생성 성공 테스트")
    void testCreateCustomer() {
        CreateCustomerRequest request = new CreateCustomerRequest("test1@gmail.com", "test1");
        Customer expectedCustomer = new Customer(UUID.randomUUID(), "test1", "test1@gmail.com");

        when(customerRepository.save(any(Customer.class))).thenReturn(expectedCustomer);
        CustomerResponse actualCustomer = customerService.createCustomer(request);

        verify(customerRepository, times(1)).save(any(Customer.class));
        assertThat(expectedCustomer.getCustomerId()).isEqualTo(actualCustomer.customerId());
        assertThat(expectedCustomer.getEmail()).isEqualTo(actualCustomer.email());
        assertThat(expectedCustomer.getName()).isEqualTo(actualCustomer.name());
    }

    @Test
    @DisplayName("이미 존재하는 email이면 고객을 생성할 수 없다")
    void testDuplicatedEmail() {
        CreateCustomerRequest request = new CreateCustomerRequest("test1@gmail.com", "test1");
        Customer existingCustomer = new Customer(UUID.randomUUID(), "test1", "test1@gmail.com");

        when(customerRepository.findCustomerByEmail("test1@gmail.com")).thenReturn(Optional.of(existingCustomer));

        assertThatThrownBy(() -> customerService.createCustomer(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Email already exists: test1@gmail.com");
        verify(customerRepository).findCustomerByEmail("test1@gmail.com");
    }

    @Test
    void findAllBlackList() {
        Customer customer = new Customer(UUID.randomUUID(), "test1", "test1@gmail.com");
        customer.toBlackList();

        when(customerRepository.findAllBlackList()).thenReturn(List.of(customer));
        List<CustomerResponse> blackListCustomers = customerService.findAllBlackList();

        assertThat(blackListCustomers).hasSize(1);
        assertThat(blackListCustomers.get(0).isBlackList()).isTrue();
    }
}
