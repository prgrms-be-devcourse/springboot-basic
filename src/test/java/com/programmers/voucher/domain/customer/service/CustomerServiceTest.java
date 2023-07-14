package com.programmers.voucher.domain.customer.service;

import com.programmers.voucher.domain.customer.domain.Customer;
import com.programmers.voucher.domain.customer.dto.CustomerDto;
import com.programmers.voucher.domain.customer.repository.CustomerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DuplicateKeyException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
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
        customerService.createCustomer("customer@gmail.com", "customer");

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
        assertThatThrownBy(() -> customerService.createCustomer("customer@gmailc.om", "customer"))
                .isInstanceOf(DuplicateKeyException.class);
    }

    @Test
    @DisplayName("성공: Customer 단건 업데이트")
    void update() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "customer@gmail.com", "customer");
        given(customerRepository.findById(any())).willReturn(Optional.of(customer));

        //when
        customerService.updateCustomer(customer.getCustomerId(), "updatedName", false);

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
        assertThatThrownBy(() -> customerService.updateCustomer(customer.getCustomerId(), "updatedName", false))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("성공: Customer 목록 조회")
    void findCustomers() {
        //given
        Customer customerA = new Customer(UUID.randomUUID(), "customerA@gmail.com", "customerA");
        Customer customerB = new Customer(UUID.randomUUID(), "customerB@gmail.com", "customerB");
        List<Customer> customers = List.of(customerA, customerB);

        given(customerRepository.findAll()).willReturn(customers);

        //when
        List<CustomerDto> findCustomers = customerService.findCustomers();

        //then
        CustomerDto customerDtoA = CustomerDto.from(customerA);
        CustomerDto customerDtoB = CustomerDto.from(customerB);

        assertThat(findCustomers).usingRecursiveFieldByFieldElementComparator()
                .contains(customerDtoA, customerDtoB);
    }

    @Test
    @DisplayName("성공: Customer 단건 삭제")
    void delete() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "customer@gmail.com", "customer");

        given(customerRepository.findById(any())).willReturn(Optional.of(customer));

        //when
        customerService.deleteCustomer(customer.getCustomerId());

        //then
        then(customerRepository).should().deleteById(any());
    }

    @Test
    @DisplayName("예외: Customer 단건 삭제 - 존재하지 않는 customer")
    void delete_ButNoSuchElement_Then_Exception() {
        //given
        given(customerRepository.findById(any())).willReturn(Optional.empty());

        //when
        //then
        assertThatThrownBy(() -> customerService.deleteCustomer(UUID.randomUUID()))
                .isInstanceOf(NoSuchElementException.class);
    }
}