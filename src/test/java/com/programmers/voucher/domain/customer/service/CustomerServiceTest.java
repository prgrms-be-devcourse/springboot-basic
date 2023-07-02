package com.programmers.voucher.domain.customer.service;

import com.programmers.voucher.domain.customer.domain.Customer;
import com.programmers.voucher.domain.customer.repository.CustomerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DuplicateKeyException;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Test
    @DisplayName("성공: Customer 단건 저장")
    void save() {
        //given
        given(customerRepository.findByEmail(any())).willReturn(Optional.empty());

        //when
        customerService.save("customer@gmail.com", "customer");

        //then
        then(customerRepository).should().findByEmail(any());
        then(customerRepository).should().save(any());
    }
    
    @Test
    @DisplayName("예외: Customer 단건 저장 - 중복된 이메일")
    void save_ButDuplicateEmail_Then_Exception() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "customer@gmail.com", "customer");
        given(customerRepository.findByEmail(any())).willReturn(Optional.of(customer));
        
        //when
        //then
        assertThatThrownBy(() -> customerService.save("customer@gmailc.om", "customer"))
                .isInstanceOf(DuplicateKeyException.class);
    }

    @Test
    @DisplayName("성공: Customer 단건 업데이트")
    void update() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "customer@gmail.com", "customer");
        given(customerRepository.findById(any())).willReturn(Optional.of(customer));

        //when
        customerService.update(customer.getCustomerId(), "updatedName");

        //then
        then(customerRepository).should().findById(any());
        then(customerRepository).should().update(any());
    }

    @Test
    @DisplayName("성공: Customer 단건 업데이트 - 존재하지 않는 customer")
    void update_ButNoSuchElement_Then_Exception() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "customer@gmail.com", "customer");
        given(customerRepository.findById(any())).willReturn(Optional.empty());

        //when
        //then
        assertThatThrownBy(() -> customerService.update(customer.getCustomerId(), "updatedName"))
                .isInstanceOf(NoSuchElementException.class);
    }
}