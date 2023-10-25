package com.zerozae.voucher.service;

import com.zerozae.voucher.domain.customer.Customer;
import com.zerozae.voucher.domain.customer.CustomerType;
import com.zerozae.voucher.dto.customer.CustomerRequest;
import com.zerozae.voucher.dto.customer.CustomerResponse;
import com.zerozae.voucher.exception.ErrorMessage;
import com.zerozae.voucher.repository.customer.CustomerRepository;
import com.zerozae.voucher.repository.customer.FileCustomerRepository;
import com.zerozae.voucher.repository.customer.MemoryCustomerRepository;
import com.zerozae.voucher.service.customer.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerServiceTest {
    CustomerRepository customerRepository;
    CustomerService customerService;
    Customer normalCustomer;
    Customer blackCustomer;

    @BeforeEach
    void setUp() {
        customerRepository = mock(MemoryCustomerRepository.class);
        customerService = new CustomerService(customerRepository);
    }

    @Test
    @DisplayName("회원 등록 메서드 호출 성공 테스트")
    void createCustomer_Success_Test() {
        // Given
        CustomerRequest customerRequest = new CustomerRequest("고객1", CustomerType.NORMAL);

        when(customerRepository.save(any(Customer.class))).thenReturn(normalCustomer);

        // When
        CustomerResponse customerResponse = customerService.createCustomer(customerRequest);

        // Then
        assertEquals(normalCustomer.getCustomerName(), customerResponse.getCustomerName());
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    @DisplayName("회원 전체 조회 메서드 호출 성공 테스트")
    void findAllBlacklist_Success_Test() {
        // Given
        List<Customer> customerList = List.of(
            normalCustomer,
            blackCustomer
        );
        when(customerRepository.findAll()).thenReturn(customerList);

        // When
        List<CustomerResponse> customers = customerService.findAllCustomers();

        // Then
        assertEquals(2, customers.size());
        assertTrue(customers.stream().anyMatch(c -> c.getCustomerName().equals(normalCustomer.getCustomerName())));
        assertTrue(customers.stream().anyMatch(c -> c.getCustomerName().equals(normalCustomer.getCustomerName())));
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("블랙리스트 회원 조회 메서드 테스트")
    void findBlacklistCustomer_Success_Test() {
        // Given
        List<Customer> customerList = List.of(
                normalCustomer,
                blackCustomer
        );
        when(customerRepository.findAll()).thenReturn(List.of(customerList.get(1)));

        // When
        List<CustomerResponse> customers = customerService.findAllBlacklistCustomers();

        // Then
        assertEquals(1, customers.size());
        assertTrue(customers.stream().anyMatch(c -> c.getCustomerName().equals(blackCustomer.getCustomerName())));
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("회원 중복 검사 예외 발생 테스트")
    void validateDuplicateCustomer_Test() {
        // Given
        CustomerRequest customerRequest = new CustomerRequest("고객2", CustomerType.BLACKLIST);

        when(customerRepository.findAll()).thenReturn(List.of(blackCustomer));

        // When & Then
        assertThrows(ErrorMessage.class, () -> {
            customerService.createCustomer(customerRequest);
        });
    }

    @Test
    @DisplayName("회원 아이디로 회원 찾기 테스트")
    void findCustomerById_Success_Test() {
        // Given
        Customer customer = normalCustomer;

        when(customerRepository.findById(customer.getCustomerId())).thenReturn(Optional.of(customer));

        // When
        CustomerResponse customerResponse = customerService.findById(customer.getCustomerId());

        // Then
        assertEquals(customerResponse.getCustomerType(), customer.getCustomerType());
        assertEquals(customerResponse.getCustomerName(), customer.getCustomerName());
        verify(customerRepository, times(1)).findById(customer.getCustomerId());
    }

    @Test
    @DisplayName("존재하지 않는 회원 아이디로 회원 조회 시 예외 발생 테스트")
    void findCustomer_NotExistId_Failed_Test() {
        // Given
        UUID notExistId = UUID.randomUUID();
        when(customerRepository.findById(notExistId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ErrorMessage.class,  () ->  {
            customerService.findById(notExistId);
        });
        verify(customerRepository, times(1)).findById(notExistId);
    }

    @Test
    @DisplayName("회원 아이디로 회원 삭제 테스트")
    void deleteCustomerById_Success_Test() {
        // Given
        Customer customer = normalCustomer;
        when(customerRepository.findById(customer.getCustomerId())).thenReturn(Optional.of(customer));

        // When
        customerService.deleteById(customer.getCustomerId());

        // Then
        verify(customerRepository,times(1)).findById(customer.getCustomerId());
        verify(customerRepository,times(1)).delete(customer.getCustomerId());
    }

    @Test
    @DisplayName("존재하지 않는 회원 아이디로 회원 삭제 테스트")
    void deleteCustomerById_NotExist_Failed_Test() {
        // Given
        UUID notExistId = UUID.randomUUID();
        when(customerRepository.findById(notExistId)).thenReturn(Optional.empty());

        // Then & When
        assertThrows(ErrorMessage.class, () -> {
            customerService.deleteById(notExistId);
        });
        verify(customerRepository,times(1)).findById(notExistId);
    }

    @Test
    @DisplayName("회원 전체 삭제 테스트")
    void deleteAllCustomers_Success_Test() {
        // Given

        // When
        customerService.deleteAll();

        // Then
        verify(customerRepository, times(1)).deleteAll();
    }

    @Test
    @DisplayName("회원 업데이트 호출 테스트")
    void updateCustomer_Success_Test() {
        // Given
        CustomerRequest updateRequest = new CustomerRequest("고객2", CustomerType.BLACKLIST);
        Customer customer = normalCustomer;
        when(customerRepository.findById(customer.getCustomerId())).thenReturn(Optional.of(customer));

        // When
        customerService.update(customer.getCustomerId(), updateRequest);

        // Then
        verify(customerRepository, times(1)).findById(customer.getCustomerId());
        verify(customerRepository, times(1)).update(customer.getCustomerId(), updateRequest);
    }

    @Test
    @DisplayName("회원 업데이트 실패 테스트")
    void updateCustomer_NotExistId_Failed_Test() {
        // Given
        CustomerRequest updateRequest = new CustomerRequest("고객2", CustomerType.BLACKLIST);
        UUID notExistId = UUID.randomUUID();
        when(customerRepository.findById(notExistId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ErrorMessage.class, () -> {
            customerService.update(notExistId, updateRequest);
        });
        verify(customerRepository, times(1)).findById(notExistId);
    }
}
