package org.prgms.voucheradmin.domain.customer.service;

import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgms.voucheradmin.domain.customer.dao.customer.CustomerRepository;
import org.prgms.voucheradmin.domain.customer.dto.CustomerUpdateReqDto;
import org.prgms.voucheradmin.domain.customer.entity.Customer;
import org.prgms.voucheradmin.global.exception.CustomerNotFoundException;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {
    @InjectMocks
    CustomerService customerService;

    @Mock
    CustomerRepository customerRepository;

    @Test
    @DisplayName("고객 업데이트 예외 확인")
    void testUpdateCustomerException(){
        try {
            // when
            UUID customerId = UUID.randomUUID();
            Customer customer = new Customer(customerId, "test", "test@gmail.com", LocalDateTime.now());
            when(customerRepository.findById(customerId)).thenThrow(new CustomerNotFoundException(customerId));

            // given
            customerService.updateCustomer(new CustomerUpdateReqDto(customerId, "test1"));

            // then
            verify(customerRepository, never()).update(customer);
        }catch (CustomerNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    @DisplayName("고객 업데이트 확인")
    void testUpdateCustomer(){
        try {
            // when
            UUID customerId = UUID.randomUUID();
            Customer customer = new Customer(customerId, "test", "test@gmail.com", LocalDateTime.now());
            when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

            // given
            customerService.updateCustomer(new CustomerUpdateReqDto(customerId, "test1"));

            // then
            verify(customerRepository).update(customer);
        }catch (CustomerNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}