package org.prgrms.springbootbasic.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgrms.springbootbasic.mapper.CustomerDtoMapper;
import org.prgrms.springbootbasic.dto.CustomerInputDto;
import org.prgrms.springbootbasic.dto.CustomerUpdateDto;
import org.prgrms.springbootbasic.entity.Customer;
import org.prgrms.springbootbasic.exception.CustomerNotFoundException;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {
    @MockBean
    CustomerRepository customerRepository;

    @Autowired
    CustomerService customerService;

    @Test
    @DisplayName("customer 생성")
    void testCreateCustomer() {
        CustomerInputDto customerInputDto = new CustomerInputDto("name", "email@naver.com");
        Customer newCustomer = CustomerDtoMapper.inputDtoToCustomer(customerInputDto);

        when(customerRepository.insert(isA(Customer.class)))
                .thenReturn(newCustomer);
        Customer createdCustomer = customerService.createCustomer(customerInputDto);

        assertThat(createdCustomer, samePropertyValuesAs(newCustomer));
        verify(customerRepository, times(1)).insert(isA(Customer.class));
    }

    @Test
    @DisplayName("customer 모두 조회")
    void testLookupCustomerList() {
        List<Customer> customerList = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            customerList.add(
                    Customer.builder()
                            .customerId(UUID.randomUUID())
                            .name("name" + i)
                            .email("email" + i)
                            .createdAt(LocalDateTime.now())
                            .lastLoginAt(LocalDateTime.now())
                            .build());
        }

        when(customerRepository.findAll()).thenReturn(customerList);
        List<Customer> lookedUpList = customerService.getCustomerList();

        assertThat(lookedUpList, samePropertyValuesAs(customerList));
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    @DisplayName(" customerId로 조회 - id에 해당하는 customer 존재")
    void testFindCustomerById_exist() {

        UUID customerId = UUID.randomUUID();

        Customer targetCustomer = Customer.builder()
                .customerId(customerId)
                .name("name1")
                .email("email1")
                .lastLoginAt(LocalDateTime.now())
                .createdAt(LocalDateTime.now()).build();

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(targetCustomer));

        Customer customerById = customerService.getCustomerById(customerId.toString());

        assertThat(customerById, samePropertyValuesAs(targetCustomer));
        verify(customerRepository, times(1)).findById(customerId);
    }

    @Test
    @DisplayName("customerId로 조회 - id에 해당하는 customer 존재하지 않음")
    void testFindCustomerById_not_exist() {
        UUID customerId = UUID.randomUUID();

        when(customerRepository.findById(customerId))
                .thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class,
                () -> customerService.getCustomerById(customerId.toString()));
        verify(customerRepository, times(1)).findById(customerId);
    }

    @Test
    @DisplayName("update 성공")
    void testUpdateCustomer_success() {
        UUID customerId = UUID.randomUUID();

        Customer updatedCustomer = Customer.builder()
                .customerId(customerId)
                .name("updatedName")
                .email("email1@naver.com")
                .createdAt(LocalDateTime.now())
                .lastLoginAt(LocalDateTime.now())
                .build();

        when(customerRepository.findById(any(UUID.class)))
                .thenReturn(Optional.of(updatedCustomer));

        when(customerRepository.update(isA(Customer.class)))
                .thenReturn(Optional.of(updatedCustomer));

        Customer customer = customerService.editCustomer(
                new CustomerUpdateDto(
                        updatedCustomer.getCustomerId().toString(),
                        updatedCustomer.getName(),
                        updatedCustomer.getEmail(),
                        updatedCustomer.getCreatedAt(),
                        updatedCustomer.getLastLoginAt()
                ));

        assertThat(updatedCustomer, samePropertyValuesAs(customer));
        verify(customerRepository, times(1)).findById(any(UUID.class));
        verify(customerRepository, times(1)).update(any(Customer.class));
    }

    @Test
    @DisplayName("update 실패 - 해당하는 customer 조회 실패")
    void testUpdateCustomer_fail() {
        UUID customerId = UUID.randomUUID();

        Customer updatedCustomer = Customer.builder()
                .customerId(customerId)
                .name("updatedName")
                .email("email1@naver.com")
                .createdAt(LocalDateTime.now())
                .lastLoginAt(LocalDateTime.now())
                .build();

        when(customerRepository.findById(any(UUID.class)))
                .thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () ->
                customerService.editCustomer(
                        new CustomerUpdateDto(
                                updatedCustomer.getCustomerId().toString(),
                                updatedCustomer.getName(),
                                updatedCustomer.getEmail(),
                                updatedCustomer.getCreatedAt(),
                                updatedCustomer.getLastLoginAt()
                        ))
        );
        verify(customerRepository, times(1)).findById(any(UUID.class));
        verify(customerRepository, times(0)).update(any(Customer.class));
    }

    @Test
    @DisplayName("customer 삭제 성공")
    void deleteCustomerById_success() {
        UUID customerId = UUID.randomUUID();

        Customer targetCustomer = Customer.builder()
                .customerId(customerId)
                .name("updatedName")
                .email("email")
                .createdAt(LocalDateTime.now())
                .lastLoginAt(LocalDateTime.now())
                .build();

        when(customerRepository.findById(customerId))
                .thenReturn(Optional.of(targetCustomer));
        when(customerRepository.deleteById(customerId))
                .thenReturn(1);

        int affectedRow = customerService.removeCustomerById(customerId.toString());

        assertThat(affectedRow, is(1));
        verify(customerRepository, times(1)).deleteById(customerId);
    }

    @Test
    @DisplayName("customer 삭제 실패")
    void deleteCustomerById_fail() {
        UUID customerId = UUID.randomUUID();

        when(customerRepository.deleteById(any(UUID.class)))
                .thenReturn(1);
        assertThrows(CustomerNotFoundException.class,
                () -> customerService.removeCustomerById(customerId.toString()));

        verify(customerRepository, times(1)).findById(any(UUID.class));
        verify(customerRepository, times(0)).deleteById(any(UUID.class));
    }
}