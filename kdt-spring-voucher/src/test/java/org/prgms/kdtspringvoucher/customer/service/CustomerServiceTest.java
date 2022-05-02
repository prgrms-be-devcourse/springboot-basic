package org.prgms.kdtspringvoucher.customer.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgms.kdtspringvoucher.customer.domain.Customer;
import org.prgms.kdtspringvoucher.customer.domain.CustomerType;
import org.prgms.kdtspringvoucher.customer.repository.CustomerRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    CustomerRepository customerRepository;

    @InjectMocks
    CustomerService customerService;

    @ParameterizedTest
    @CsvSource({"a,a@email.com,BASIC", "b,b@email.com,BASIC", "c,c@email.com,BASIC", "d,d@email.com,BASIC"})
    void createCustomerTest(String name, String email, CustomerType customerType) {
        Customer customer = new Customer(UUID.randomUUID(), name, email, customerType, LocalDateTime.now());
        when(customerRepository.save(customer)).thenReturn(customer);

        Customer createdCustomer = customerService.createCustomer(name, email, customerType);

        assertThat(createdCustomer, is(customer));
    }

    @ParameterizedTest
    @CsvSource({"a,a@email.com,BASIC", "b,b@email.com,BASIC", "c,c@email.com,BASIC", "d,d@email.com,BASIC"})
    void changeCustomerTypeById(String name, String email, CustomerType customerType) {
        Customer customer = new Customer(UUID.randomUUID(), name, email, customerType, LocalDateTime.now());
        Customer updateCustomer = new Customer(customer.getCustomerId(), name, email, CustomerType.BLACKLIST, LocalDateTime.now());
        when(customerRepository.findById(customer.getCustomerId())).thenReturn(Optional.of(customer));
        when(customerRepository.update(customer)).thenReturn(updateCustomer);

        Customer updatedCustomer = customerService.changeCustomerTypeById(customer, CustomerType.BLACKLIST);

        assertThat(updatedCustomer,is(updateCustomer));
    }

    @Test
    void showAllCustomer() {
        Customer blacklist = new Customer(UUID.randomUUID(), "test", "test@email.com", CustomerType.BLACKLIST, LocalDateTime.now());
        when(customerRepository.findByCustomerType(any())).thenReturn(List.of(blacklist));

        customerService.showBlackList(CustomerType.BLACKLIST);
    }
}