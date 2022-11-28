package org.prgrms.kdt.service;

import org.assertj.core.api.Assertions;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgrms.kdt.model.customer.Customer;
import org.prgrms.kdt.repository.customer.CustomerRepository;
import org.prgrms.kdt.service.customer.CustomerService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {
    @Mock
    private CustomerRepository customerRepository;
    @InjectMocks
    private CustomerService customerService;

    @Test
    @DisplayName("서비스에서 모든 고객 조회 시 레파지토리의 getAllStoredCustomer을 실행한다.")
    void 전체_고객_반환() {
        List<Customer> customerList = new ArrayList<>();

        when(customerRepository.getAllStoredCustomer()).thenReturn(customerList);
        customerService.getAllCustomers();

        verify(customerRepository).getAllStoredCustomer();
    }

    @Test
    @DisplayName("서비스에서 모든 고객 조회 시 레파지토리의 getAllStoredCustomer을 실행한다.")
    void 블랙리스트_반환() {
        List<Customer> customerList = new ArrayList<>();
        customerList.add(new Customer(UUID.randomUUID(), "test01", "test01@gmail.com", LocalDateTime.now(), true));
        customerList.add(new Customer(UUID.randomUUID(), "test02", "test02@gmail.com", LocalDateTime.now(), false));
        customerList.add(new Customer(UUID.randomUUID(), "test03", "test03@gmail.com", LocalDateTime.now(), true));
        customerList.add(new Customer(UUID.randomUUID(), "test04", "test04@gmail.com", LocalDateTime.now(), false));

        when(customerRepository.getAllStoredCustomer()).thenReturn(customerList);
        List<Customer> allBlacklist = customerService.getAllBlacklist();

        verify(customerRepository).getAllStoredCustomer();
        Assertions.assertThat(allBlacklist).filteredOn(Customer::isBlacklist)
                .hasSize(2);
    }

    @Test
    @DisplayName("Customer Id를 이용해서 customer를 찾을 수 있다.")
    void ID_이용해서_customer찾기() {
        Customer customer = new Customer(UUID.randomUUID(), "test01", "test01@gmail.com", LocalDateTime.now(), true);
        customerRepository.insert(customer);

        when(customerRepository.findById(customer.getCustomerId())).thenReturn(Optional.of(customer));
        Customer findCustomer = customerService.findCustomerById(customer.getCustomerId().toString());

        verify(customerRepository).findById(customer.getCustomerId());
        MatcherAssert.assertThat(customer, samePropertyValuesAs(findCustomer));
    }
}