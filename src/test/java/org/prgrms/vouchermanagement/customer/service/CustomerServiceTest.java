package org.prgrms.vouchermanagement.customer.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.vouchermanagement.customer.domain.Customer;
import org.prgrms.vouchermanagement.customer.repository.CustomerRepository;
import org.prgrms.vouchermanagement.exception.customer.CustomerAlreadyExistException;
import org.prgrms.vouchermanagement.exception.customer.CustomerNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CustomerServiceTest {

    private final CustomerRepository customerRepository = mock(CustomerRepository.class);
    private final CustomerService customerService = new CustomerService(customerRepository);

    @Test
    @DisplayName("모든 Customer 찾기 확인")
    void findAll() {
        // given
        Customer customerA = createCustomer(UUID.randomUUID(), "customerA", "customerA@google.com");
        Customer customerB = createCustomer(UUID.randomUUID(), "customerB", "customerB@google.com");

        when(customerRepository.findAll())
                .thenReturn(List.of(customerA, customerB));

        // when
        List<Customer> customers = customerService.findAll();

        // then
        verify(customerRepository).findAll();
        assertThat(customers).hasSize(2);
        assertThat(customers).extracting("customerId", "name", "email", "createdAt")
                .contains(tuple(customerA.getCustomerId(), customerA.getName(), customerA.getEmail(), customerA.getCreatedAt()))
                .contains(tuple(customerB.getCustomerId(), customerB.getName(), customerB.getEmail(), customerB.getCreatedAt()));
    }

    @Test
    @DisplayName("Customer 저장 시 이미 존재하는 이메일일 경우 확인")
    void save() {
        // given
        String name = "customerA";
        String email = "customerA@google.com";
        Customer customerA = createCustomer(UUID.randomUUID(), name, email);

        when(customerRepository.isPresent(customerA.getEmail()))
                .thenThrow(CustomerAlreadyExistException.class);

        // when, then
        assertThrows(CustomerAlreadyExistException.class, () -> customerService.save(name, email));
    }

    @Test
    @DisplayName("Customer 수정 확인")
    void update() {
        // given
        Customer beforeCustomer = createCustomer(UUID.randomUUID(), "beforeName", "beforeEmail@gmail.com");
        Customer afterCustomer = createCustomer(beforeCustomer.getCustomerId(), "afterName", "afterEmail@gmail.com");

        when(customerRepository.update(beforeCustomer))
                .thenReturn(afterCustomer);
        Customer updatedCustomer = customerService.update(beforeCustomer);

        // when, then
        assertThat(updatedCustomer)
                .usingRecursiveComparison()
                .isEqualTo(afterCustomer);
    }

    @Test
    @DisplayName("CustomerId로 Customer 찾기 확인")
    void findById() {
        // given
        Customer customer = createCustomer(UUID.randomUUID(), "name", "email@gmail.com");
        when(customerRepository.findById(customer.getCustomerId()))
                .thenReturn(Optional.of(customer));

        // when
        Customer findCustomer = customerService.findById(customer.getCustomerId());

        // then
        assertThat(customer)
                .usingRecursiveComparison()
                .isEqualTo(findCustomer);
    }

    @Test
    @DisplayName("CustomerId로 Customer 찾을 떄 Customer가 존재하지 않는 경우")
    void findByIdWhenCustomerNotExist() {
        // given
        UUID customerId = UUID.randomUUID();
        Customer customer = createCustomer(customerId, "name", "email@gmail.com");
        when(customerRepository.findById(customer.getCustomerId()))
                .thenThrow(CustomerNotFoundException.class);

        // when, then
        assertThrows(CustomerNotFoundException.class, () -> customerService.findById(customerId));
    }

    @Test
    @DisplayName("name으로 Customer 찾기 확인")
    void findByName() {
        // given
        String name = "name";
        Customer customerA = createCustomer(UUID.randomUUID(), name, "email@gmail.com");
        Customer customerB = createCustomer(UUID.randomUUID(), name, "email@naver.com");

        List<Customer> customers = List.of(customerA, customerB);

        when(customerRepository.findByName(name))
                .thenReturn(customers);

        // when
        assertThat(customers).extracting("customerId", "name", "email", "createdAt")
                .contains(tuple(customerA.getCustomerId(), customerA.getName(), customerA.getEmail(), customerA.getCreatedAt()))
                .contains(tuple(customerB.getCustomerId(), customerB.getName(), customerB.getEmail(), customerB.getCreatedAt()));
    }

    @Test
    @DisplayName("Email로 Customer 찾기 확인")
    void findByEmail() {
        // given
        Customer customerA = createCustomer(UUID.randomUUID(), "customerA", "emailA@gmail.com");

        when(customerRepository.findByEmail(customerA.getEmail()))
                .thenReturn(Optional.of(customerA));

        // when
        Customer findCustomer = customerService.findByEmail(customerA.getEmail());

        // then
        assertThat(customerA)
                .usingRecursiveComparison()
                .isEqualTo(findCustomer);
    }

    @Test
    @DisplayName("Email로 Customer 찾기 시 존재하지 않는 이메일인 경우")
    void findByEmailWhenNotExistEmail() {
        // given
        String email = "email@gmail.com";
        Customer customerA = createCustomer(UUID.randomUUID(), "customerA", email);

        when(customerRepository.findByEmail(customerA.getEmail()))
                .thenThrow(CustomerNotFoundException.class);

        // when, then
        assertThrows(CustomerNotFoundException.class, () -> customerService.findByEmail(email));
    }

    private Customer createCustomer(UUID customerId, String name, String email) {
        return Customer.createNormalCustomer(customerId, name, email, LocalDateTime.now());
    }
}