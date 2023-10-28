package devcourse.springbootbasic.service;

import devcourse.springbootbasic.domain.customer.Customer;
import devcourse.springbootbasic.dto.customer.CustomerCreateRequest;
import devcourse.springbootbasic.dto.customer.CustomerFindResponse;
import devcourse.springbootbasic.dto.customer.CustomerUpdateBlacklistRequest;
import devcourse.springbootbasic.exception.CustomerErrorMessage;
import devcourse.springbootbasic.exception.CustomerException;
import devcourse.springbootbasic.repository.customer.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static devcourse.springbootbasic.TestDataFactory.generateBlacklistCustomer;
import static devcourse.springbootbasic.TestDataFactory.generateNotBlacklistCustomers;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;
    @Mock
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("블랙리스트 고객 조회 시 전체 블랙리스트 고객 Response DTO를 반환합니다.")
    void givenBlacklistedCustomersExist_whenFindAllBlacklistedCustomers_thenReturnListOfBlacklistedCustomers() {
        // Given
        Customer customer1 = generateBlacklistCustomer("Customer 1");
        Customer customer2 = generateBlacklistCustomer("Customer 2");
        when(customerRepository.findAllBlacklistedCustomers()).thenReturn(List.of(customer1, customer2));

        // When
        List<CustomerFindResponse> blacklistedCustomers = customerService.findAllBlacklistedCustomers();

        // Then
        assertThat(blacklistedCustomers).hasSize(2);
    }

    @Test
    @DisplayName("고객 생성 시 생성된 고객을 반환합니다.")
    void givenValidCustomerCreateRequest_whenCreateCustomer_thenCustomerIsCreated() {
        // Given
        String name = "New Customer";
        CustomerCreateRequest request = new CustomerCreateRequest(name);
        Customer createdCustomer = generateNotBlacklistCustomers(name);
        when(customerRepository.save(any())).thenReturn(createdCustomer);

        // When
        Customer result = customerService.createCustomer(request);

        // Then
        assertThat(result).isEqualTo(createdCustomer);
    }

    @Test
    @DisplayName("블랙리스트 업데이트 시 블랙리스트 상태가 업데이트된 고객을 반환합니다.")
    void givenValidBlacklistUpdateRequest_whenUpdateBlacklistStatus_thenBlacklistStatusIsUpdated() {
        // Given
        CustomerUpdateBlacklistRequest request = new CustomerUpdateBlacklistRequest(UUID.randomUUID(), true);
        Customer existingCustomer = generateBlacklistCustomer("Existing Customer");
        when(customerRepository.findById(request.getId())).thenReturn(Optional.of(existingCustomer));
        when(customerRepository.update(any())).thenReturn(1);

        // When
        Customer updatedCustomer = customerService.updateBlacklistStatus(request);

        // Then
        assertThat(updatedCustomer.getId()).isEqualTo(request.getId());
        assertThat(updatedCustomer.isBlacklisted()).isEqualTo(request.isBlacklisted());
        verify(customerRepository, times(1)).update(existingCustomer);
    }

    @Test
    @DisplayName("블랙리스트 업데이트 시 존재하지 않는 고객 ID가 주어지면 예외를 발생시킵니다.")
    void givenNonExistentCustomerId_whenUpdateBlacklistStatus_thenThrowException() {
        // Given
        CustomerUpdateBlacklistRequest request = new CustomerUpdateBlacklistRequest(UUID.randomUUID(), true);
        when(customerRepository.findById(request.getId())).thenReturn(Optional.empty());

        // When, Then
        assertThatThrownBy(() -> customerService.updateBlacklistStatus(request))
                .isInstanceOf(CustomerException.class)
                .hasMessageContaining(CustomerErrorMessage.NOT_FOUND.getMessage());
    }
}
