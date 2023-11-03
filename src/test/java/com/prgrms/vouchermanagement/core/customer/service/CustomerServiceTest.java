package com.prgrms.vouchermanagement.core.customer.service;

import com.prgrms.vouchermanagement.core.customer.domain.Customer;
import com.prgrms.vouchermanagement.core.customer.dto.CustomerDto;
import com.prgrms.vouchermanagement.core.customer.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CustomerServiceTest {

    private CustomerService customerService;
    private CustomerRepository customerRepository;

    @BeforeEach
    public void setUp() {
        customerRepository = mock(CustomerRepository.class);
        customerService = new CustomerService(customerRepository);
    }

    @DisplayName("고객을 추가할 수 있다.")
    @Test
    void testCreate() {
        // given
        String name = "sujin";
        String email = "sujin@email.com";
        CustomerDto customerDto = new CustomerDto(name, email);
        when(customerRepository.save(any(Customer.class))).thenReturn(new Customer(UUID.randomUUID().toString(), name, email));

        // when
        CustomerDto savedCustomer = customerService.create(customerDto);

        // then
        verify(customerRepository, times(1)).save(any(Customer.class));
        assertThat(savedCustomer).isEqualTo(customerDto);
    }

    @DisplayName("id로 고객을 조회할 수 있다.")
    @Test
    void testFindById() {
        // given
        String id = UUID.randomUUID().toString();
        Customer customer = new Customer(id, "sujin", "sujin@email.com");

        when(customerRepository.findById(id)).thenReturn(Optional.of(customer));

        // when
        CustomerDto customerDto = customerService.findById(id);
        Customer findCustomer = CustomerDto.toCustomer(customerDto);

        // then
        assertThat(findCustomer).isEqualTo(customer);
    }

    @DisplayName("id로 고객을 조회시 고객이 없으면 예외를 발생한다.")
    @Test
    void testFindByIdException() {
        // given
        String id = UUID.randomUUID().toString();

        when(customerRepository.findById(id)).thenReturn(Optional.empty());

        // when

        // then
        assertThrows(IllegalArgumentException.class, () -> customerService.findById(id));
    }

    @DisplayName("전체 고객을 조회할 수 있다.")
    @Test
    void testFindAll() {
        // given
        Customer customer1 = new Customer(UUID.randomUUID().toString(), "customer1", "customer1@email.com");
        Customer customer2 = new Customer(UUID.randomUUID().toString(), "customer2", "customer2@email.com");
        CustomerDto customerDto1 = CustomerDto.toCustomerDto(customer1);
        CustomerDto customerDto2 = CustomerDto.toCustomerDto(customer2);

        when(customerRepository.findAll()).thenReturn(List.of(customer1, customer2));

        // when
        List<CustomerDto> customerDtoList = customerService.findAll();

        // then
        assertThat(customerDtoList.containsAll(List.of(customerDto1, customerDto2)));
    }
}