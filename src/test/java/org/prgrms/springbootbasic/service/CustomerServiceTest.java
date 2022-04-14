package org.prgrms.springbootbasic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgrms.springbootbasic.entity.Customer;
import org.prgrms.springbootbasic.exception.DuplicateCustomerEmailException;
import org.prgrms.springbootbasic.repository.customer.CustomerRepository;


@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    CustomerRepository customerRepository;

    CustomerService customerService;

    @BeforeEach
    void init() {
        customerService = new CustomerService(customerRepository);
    }

    @DisplayName("회원 생성 테스트 - 정상")
    @Test
    void creatCustomer() {
        //given
        String email = "test@gmail.com";
        when(customerRepository.findByEmail(email)).thenReturn(Optional.empty());

        //when
        customerService.createCustomer("test", email);

        //then
        var inOrder = inOrder(customerRepository);
        inOrder.verify(customerRepository).findByEmail(email);
        inOrder.verify(customerRepository).save(any(Customer.class));
    }

    @DisplayName("회원 생성 테스트 - 이메일 중복")
    @Test
    void createCustomerException() {
        //given
        String email = "test@gmail.com";
        when(customerRepository.findByEmail(email))
            .thenReturn(Optional.of(new Customer(UUID.randomUUID(), "a", email)));

        //when
        //then
        assertThatThrownBy(() -> customerService.createCustomer("test", email))
            .isInstanceOf(DuplicateCustomerEmailException.class);
    }

    @DisplayName("모든 회원 조회 테스트")
    @Test
    void findAllCustomers() {
        //given
        var customer1 = new Customer(UUID.randomUUID(), "test", "test@gmail.com");
        var customer2 = new Customer(UUID.randomUUID(), "test01", "test01@gmail.com");

        List<Customer> customers = List.of(customer1, customer2);
        when(customerRepository.findAll()).thenReturn(customers);

        //when
        var allCustomers = customerService.findAllCustomers();

        //then
        assertThat(allCustomers)
            .containsExactlyInAnyOrder(customer1, customer2);
    }
}