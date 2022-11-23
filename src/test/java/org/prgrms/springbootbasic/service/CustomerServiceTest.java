package org.prgrms.springbootbasic.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgrms.springbootbasic.CustomerInputDto;
import org.prgrms.springbootbasic.entity.Customer;
import org.prgrms.springbootbasic.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    CustomerInputDto customerInputDto;

    @MockBean
    CustomerRepository customerRepository;

    @Autowired
    CustomerService customerService;


    @Test
    @DisplayName("customer 생성")
    void testCreateCustomer() {

        UUID uuid = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        Customer newCustomer = new Customer.Builder()
                .customerId(uuid)
                .email("email1")
                .name("name1")
                .createdAt(now)
                .lastLoginAt(now)
                .build();

        when(customerInputDto.toCustomer()).thenReturn(newCustomer);
        when(customerRepository.insert(newCustomer)).thenReturn(newCustomer);

        Customer createdCustomer = customerService.createCustomer(customerInputDto);

        assertThat(createdCustomer, is(newCustomer));
        verify(customerInputDto, times(1)).toCustomer();
        verify(customerRepository, times(1)).insert(newCustomer);
    }

    @Test
    @DisplayName("customer 모두 조회")
    void testLookupCustomerList() {
        List<Customer> customerList = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            customerList.add(
                    new Customer.Builder()
                            .customerId(UUID.randomUUID())
                            .name("name" + i)
                            .email("email" + i)
                            .createdAt(LocalDateTime.now())
                            .lastLoginAt(LocalDateTime.now())
                            .build());
        }

        when(customerRepository.findAll()).thenReturn(customerList);
        List<Customer> lookedUpList = customerService.lookupCustomerList();

        assertThat(lookedUpList, samePropertyValuesAs(customerList));
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    @DisplayName(" customerId로 조회 - id에 해당하는 customer 존재")
    void testFindCustomerById_exist() {

        UUID customerId = UUID.randomUUID();

        Customer targetCustomer = new Customer.Builder()
                .customerId(customerId)
                .name("name1")
                .email("email1")
                .lastLoginAt(LocalDateTime.now())
                .createdAt(LocalDateTime.now()).build();

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(targetCustomer));

        Optional<Customer> customerById = customerService.findCustomerById(customerId.toString());

        assertThat(customerById.isPresent(), is(true));
        verify(customerRepository, times(1)).findById(customerId);
    }

    @Test
    @DisplayName(" customerId로 조회 - id에 해당하는 customer 존재하지 않음")
    void testFindCustomerById_not_exist() {

        UUID customerId = UUID.randomUUID();

        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        Optional<Customer> customerById = customerService.findCustomerById(customerId.toString());

        assertThat(customerById.isEmpty(), is(true));
        verify(customerRepository, times(1)).findById(customerId);
    }

    @Test
    void testUpdateCustomer() {
        UUID customerId = UUID.randomUUID();

        Customer updatedCustomer = new Customer.Builder()
                .customerId(customerId)
                .name("updatedName")
                .email("email1")
                .createdAt(LocalDateTime.now())
                .lastLoginAt(LocalDateTime.now())
                .build();

        when(customerRepository.update(updatedCustomer)).thenReturn(Optional.of(updatedCustomer));
        Optional<Customer> optionalCustomer = customerService.updateCustomer(updatedCustomer);

        assertThat(updatedCustomer, samePropertyValuesAs(optionalCustomer.get()));
        verify(customerRepository, times(1)).update(updatedCustomer);
    }

    @Test
    void deleteCustomerById() {
        UUID customerId = UUID.randomUUID();

        Customer targetCustomer = new Customer.Builder()
                .customerId(customerId)
                .name("updatedName")
                .email("email1")
                .createdAt(LocalDateTime.now())
                .lastLoginAt(LocalDateTime.now())
                .build();

        when(customerRepository.deleteById(targetCustomer.getCustomerId())).thenReturn(1);
        int affectedRow = customerService.deleteCustomerById(targetCustomer);

        assertThat(affectedRow, is(1));
        verify(customerRepository, times(1)).deleteById(targetCustomer.getCustomerId());
    }
}