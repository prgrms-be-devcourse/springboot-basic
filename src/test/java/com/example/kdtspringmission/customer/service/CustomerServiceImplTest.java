package com.example.kdtspringmission.customer.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.example.kdtspringmission.customer.domain.Customer;
import com.example.kdtspringmission.customer.repository.CustomerRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

public class CustomerServiceImplTest {


    @Test
    @DisplayName("모든 회원 정보의 리스트를 반환한다")
    void testAllCustomers() {

        // given
        List<Customer> customers = new ArrayList<>() {{
            add(new Customer(UUID.randomUUID(), "tester1", "tester1@test.com",
                LocalDateTime.now()));
            add(new Customer(UUID.randomUUID(), "tester2", "tester2@test.com",
                LocalDateTime.now()));
            add(new Customer(UUID.randomUUID(), "tester3", "tester3@test.com",
                LocalDateTime.now()));
        }};
        CustomerRepository customerRepository = mock(CustomerRepository.class);
        when(customerRepository.findAll()).thenReturn(customers);
        CustomerService customerService = new CustomerService(customerRepository);

        // when
        List<Customer> allCustomers = customerService.allCustomers();

        // then
        assertThat(allCustomers).hasSameElementsAs(customers);
        verify(customerRepository).findAll();
    }

}
