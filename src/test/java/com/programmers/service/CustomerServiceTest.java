package com.programmers.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.programmers.domain.customer.Customer;
import com.programmers.domain.customer.dto.CustomerCreateRequestDto;
import com.programmers.domain.customer.dto.CustomerResponseDto;
import com.programmers.domain.customer.dto.CustomerUpdateRequestDto;
import com.programmers.domain.customer.dto.CustomersResponseDto;
import com.programmers.repository.customer.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    private CustomerService customerService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        customerService = new CustomerService(customerRepository);
    }

    @DisplayName("회원을 저장한다")
    @Test
    public void save() {
        //given
        CustomerCreateRequestDto customerCreateRequestDto = new CustomerCreateRequestDto("testName");

        Customer customer = new Customer(UUID.randomUUID(), "testName");
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        //when
        CustomerResponseDto customerResponseDto = customerService.save(customerCreateRequestDto);

        //then
        assertThat(customer.getCustomerId(), is(customerResponseDto.id()));
        assertThat(customer.getCustomerName(), is(customerResponseDto.name()));
    }

    @DisplayName("저장된 모든 회원을 조회한다")
    @Test
    public void findAll() {
        //given
        Customer customer1 = new Customer(UUID.randomUUID(), "testName1");
        Customer customer2 = new Customer(UUID.randomUUID(), "testName2");
        List<Customer> customers = Arrays.asList(customer1, customer2);

        when(customerRepository.findAll()).thenReturn(customers);

        //when
        CustomersResponseDto customersResponseDto = customerService.findAll();

        //then
        assertThat(customers.size(), is(customersResponseDto.customers().size()));
    }

    @DisplayName("id로 회원을 조회한다")
    @Test
    public void findById() {
        //given
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer(customerId, "testName");
        Optional<Customer> optionalCustomer = Optional.of(customer);

        when(customerRepository.findById(customerId)).thenReturn(optionalCustomer);

        //when
        CustomerResponseDto customerResponseDto = customerService.findById(customerId);

        //then
        assertThat(customerId, is(customerResponseDto.id()));
        assertThat(customer.getCustomerName(), is(customerResponseDto.name()));
    }

    @DisplayName("회원을 업데이트한다")
    @Test
    public void update() {
        //given
        UUID customerId = UUID.randomUUID();
        CustomerUpdateRequestDto requestDto = new CustomerUpdateRequestDto(customerId, "name");
        Customer updatedCustomer = new Customer(customerId, "updatedCustomer");

        when(customerRepository.update(any(Customer.class))).thenReturn(updatedCustomer);

        //when
        CustomerResponseDto responseDto = customerService.update(requestDto);

        //then
        assertThat(updatedCustomer.getCustomerId(), is(responseDto.id()));
        assertThat(updatedCustomer.getCustomerName(), is(responseDto.name()));
    }
}