package com.prgrms.management.customer.service;

import com.prgrms.management.customer.domain.Customer;
import com.prgrms.management.customer.domain.CustomerType;
import com.prgrms.management.customer.repository.CustomerRepository;
import com.prgrms.management.customer.repository.FileCustomerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    CustomerRepository customerRepository=new FileCustomerRepository();
    @InjectMocks
    CustomerService customerService;

    @Test
    void 블랙_리스트_조회() {
        //given
        Customer customerOne = new Customer(CustomerType.BLACKLIST);
        Customer customerTwo = new Customer(CustomerType.BLACKLIST);
        List<Customer> customerList = Arrays.asList(customerOne,customerTwo);
        //즉시 인스턴스 객체 반환
        when(customerRepository.findBlackList()).thenReturn(customerList);
        //when
        List<Customer> blackList = customerService.findBlackList();
        //then
        Assertions.assertThat(blackList.size()).isEqualTo(2);
    }

    @Test
    void 비어있는_리스트_조회() {
        //given
        List<Customer> customerList = new ArrayList<>();
        //즉시 인스턴스 객체 반환
        when(customerRepository.findBlackList()).thenReturn(customerList);
        //when
        List<Customer> blackList = customerService.findBlackList();
        //then
        Assertions.assertThat(blackList.size()).isEqualTo(0);
    }
}