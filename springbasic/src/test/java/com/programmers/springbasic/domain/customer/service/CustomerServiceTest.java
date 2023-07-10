package com.programmers.springbasic.domain.customer.service;

import com.programmers.springbasic.domain.customer.dto.request.CustomerCreateRequestDTO;
import com.programmers.springbasic.domain.customer.dto.request.CustomerDeleteRequestDTO;
import com.programmers.springbasic.domain.customer.dto.request.CustomerSingleFindRequestDTO;
import com.programmers.springbasic.domain.customer.dto.request.CustomerUpdateRequestDTO;
import com.programmers.springbasic.domain.customer.dto.response.CustomerResponseDTO;
import com.programmers.springbasic.domain.customer.entity.Customer;
import com.programmers.springbasic.domain.customer.repository.CustomerRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {
    @Mock
    CustomerRepository customerRepository;

    @Test
    void createCustomer() {
        // given
        CustomerCreateRequestDTO customerCreateRequestDTO = new CustomerCreateRequestDTO("test", "email");
        CustomerService customerService = new CustomerService(customerRepository);

        // when
        customerService.createCustomer(customerCreateRequestDTO);

        // then
        verify(customerRepository).save(ArgumentMatchers.any(Customer.class));
    }

    @Test
    void getAllInfo() {
        // given
        Customer customer1 = new Customer(UUID.randomUUID(), "test1", "test1@gmail.com");
        CustomerResponseDTO customerResponse1 = new CustomerResponseDTO(customer1);
        Customer customer2 = new Customer(UUID.randomUUID(), "test2", "test2@gmail.com");
        CustomerResponseDTO customerResponse2 = new CustomerResponseDTO(customer2);
        Customer customer3 = new Customer(UUID.randomUUID(), "test3", "test3@gmail.com");
        CustomerResponseDTO customerResponse3 = new CustomerResponseDTO(customer3);

        List<Customer> customers = Arrays.asList(customer1, customer2, customer3);

        CustomerService customerService = new CustomerService(customerRepository);
        when(customerRepository.findAll()).thenReturn(customers);

        // when
        List<CustomerResponseDTO> customerResponses = customerService.getAllInfo();

        // then
        verify(customerRepository).findAll();
        assertThat(customerResponses,
                containsInAnyOrder(samePropertyValuesAs(customerResponse1),
                        samePropertyValuesAs(customerResponse2),
                        samePropertyValuesAs(customerResponse3)));
    }

    @Test
    void findCustomer() {
        // given
        Customer customer = new Customer(UUID.randomUUID(), "test", "test@gmail.com");
        CustomerSingleFindRequestDTO customerSingleFindRequestDTO = new CustomerSingleFindRequestDTO(customer.getCustomerId().toString());
        CustomerService customerService = new CustomerService(customerRepository);
        when(customerRepository.findById(customer.getCustomerId())).thenReturn(Optional.of(customer));

        // when
        CustomerResponseDTO customerResponseDTO = customerService.findCustomer(customerSingleFindRequestDTO);

        // then
        assertThat(customerResponseDTO.getCustomerId(), is(customer.getCustomerId()));
        assertThat(customerResponseDTO.getName(), is(customer.getName()));
        assertThat(customerResponseDTO.getEmail(), is(customer.getEmail()));
        verify(customerRepository).findById(customer.getCustomerId());
    }

    @Test
    void updateCustomer() {
        // given
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer(customerId, "test", "test@gmail.com");
        CustomerUpdateRequestDTO customerUpdateRequestDTO = new CustomerUpdateRequestDTO(customerId.toString(), "newName", "newEmail");
        CustomerService customerService = new CustomerService(customerRepository);
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        // when
        customerService.updateCustomer(customerUpdateRequestDTO);

        // then
        assertThat(customer, samePropertyValuesAs(new Customer(customerId, "newName", "newEmail")));

        InOrder inOrder = inOrder(customerRepository);
        inOrder.verify(customerRepository).findById(customerId);
        inOrder.verify(customerRepository).update(customer);
    }

    @Test
    void removeCustomer() {
        // given
        UUID customerId = UUID.randomUUID();
        CustomerDeleteRequestDTO customerDeleteRequestDTO = new CustomerDeleteRequestDTO(customerId.toString());
        CustomerService customerService = new CustomerService(customerRepository);

        // when
        customerService.removeCustomer(customerDeleteRequestDTO);

        // then
        verify(customerRepository).delete(customerId);
    }
}