package com.programmers.springweekly.service.customer;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;

import com.programmers.springweekly.domain.customer.Customer;
import com.programmers.springweekly.domain.customer.CustomerType;
import com.programmers.springweekly.repository.customer.CustomerRepository;
import com.programmers.springweekly.service.CustomerService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceUnitTest {

    @Mock
    CustomerRepository customerRepository;

    @InjectMocks
    CustomerService customerService;

    @Test
    void findById() {
        // given
        Customer customer = Customer.builder()
                .customerId(UUID.randomUUID())
                .customerName("changhyeon")
                .customerEmail("changhyeon.h@kakao.com")
                .customerType(CustomerType.BLACKLIST)
                .build();

        given(customerRepository.findById(customer.getCustomerId()))
                .willReturn(Optional.of(customer));

        // when
        customerService.findById(customer.getCustomerId());

        // then
        then(customerRepository).should(times(1)).findById(customer.getCustomerId());
    }

    @Test
    void findAll() {
        // given
        Customer customer1 = Customer.builder()
                .customerId(UUID.randomUUID())
                .customerName("changhyeon")
                .customerEmail("changhyeon.h@kakao.com")
                .customerType(CustomerType.BLACKLIST)
                .build();

        Customer customer2 = Customer.builder()
                .customerId(UUID.randomUUID())
                .customerName("changhyeonh")
                .customerEmail("changhyeon.h@kakao.com")
                .customerType(CustomerType.BLACKLIST)
                .build();

        given(customerRepository.findAll())
                .willReturn(List.of(customer1, customer2));

        // when
        customerService.findAll();

        // then
        then(customerRepository).should(times(1)).findAll();
    }


    @Test
    void getBlackList() {
        // given
        Customer customer1 = Customer.builder()
                .customerId(UUID.randomUUID())
                .customerName("changhyeon")
                .customerEmail("changhyeon.h@kakao.com")
                .customerType(CustomerType.BLACKLIST)
                .build();

        Customer customer2 = Customer.builder()
                .customerId(UUID.randomUUID())
                .customerName("changhyeonh")
                .customerEmail("changhyeon.h@kakao.com")
                .customerType(CustomerType.BLACKLIST)
                .build();

        given(customerRepository.getBlackList())
                .willReturn(List.of(customer1, customer2));

        // when
        customerService.getBlackList();

        // then
        then(customerRepository).should(times(1)).getBlackList();
    }

    @Test
    void deleteById() {
        // given
        Customer customer = Customer.builder()
                .customerId(UUID.randomUUID())
                .customerName("changhyeon")
                .customerEmail("changhyeon.h@kakao.com")
                .customerType(CustomerType.BLACKLIST)
                .build();

        given(customerRepository.deleteById(customer.getCustomerId())).willReturn(1);
        given(customerRepository.existById(customer.getCustomerId())).willReturn(true);

        // when
        customerService.deleteById(customer.getCustomerId());

        // then
        then(customerRepository).should(times(1)).deleteById(customer.getCustomerId());
    }

    @Test
    void deleteAll() {
        // given
        willDoNothing().given(customerRepository).deleteAll();

        // when
        customerService.deleteAll();

        // then
        then(customerRepository).should(times(1)).deleteAll();
    }
}
