package org.weekly.weekly.customer.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;
import org.weekly.weekly.customer.domain.Customer;
import org.weekly.weekly.customer.dto.request.CustomerCreationRequest;
import org.weekly.weekly.customer.dto.request.CustomerUpdateRequest;
import org.weekly.weekly.customer.dto.response.CustomerResponse;
import org.weekly.weekly.customer.dto.response.CustomersResponse;
import org.weekly.weekly.customer.exception.CustomerException;
import org.weekly.weekly.customer.repository.CustomerRepository;
import org.weekly.weekly.customer.service.CustomerService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;

@DisplayName("CustomerService 테스트")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @InjectMocks CustomerService customerService;

    @Mock CustomerRepository customerRepository;

    @Nested
    class Create {

        private CustomerCreationRequest customerCreationRequest;

        @BeforeEach
        void init_request() {
            String email = "psy@naver.com";
            String name = "name";

            customerCreationRequest = new CustomerCreationRequest(email, name);
        }

        @Test
        @Transactional
        void 고객생성_성공_테스트() {
            // Given
            Customer customer = new Customer(UUID.randomUUID(),
                    customerCreationRequest.getName(),
                    customerCreationRequest.getEmail(),
                    LocalDateTime.now());
            CustomerResponse customerResponse = CustomerResponse.of(customer);
            Customer savedCustomer = new Customer(customer.getCustomerId(), customer.getName(), customer.getEmail(), customer.getCreateAt());

            given(customerRepository.findByEmail(customerCreationRequest.getEmail())).willReturn(Optional.empty());
            given(customerRepository.insert(any(Customer.class))).willReturn(savedCustomer);

            // When
            CustomerResponse expectResponse = customerService.createCustomer(customerCreationRequest);

            // Then
            assertThat(expectResponse).isNotNull();
            assertAll(
                    () -> assertThat(customerResponse.getEmail()).isEqualTo(expectResponse.getEmail()),
                    () -> assertThat(customerResponse.getName()).isEqualTo(expectResponse.getName())
            );
        }

        @Test
        void 중복된_이메일으로_고객생성_실패_테스트() {
            // Given
            Customer customer = new Customer(UUID.randomUUID(),
                    customerCreationRequest.getName(),
                    customerCreationRequest.getEmail(),
                    LocalDateTime.now());
            given(customerRepository.findByEmail(customerCreationRequest.getEmail())).willReturn(Optional.of(customer));

            // When
            assertThatThrownBy(() -> customerService.createCustomer(customerCreationRequest))
                    .isInstanceOf(CustomerException.class);
        }
    }

    @Nested
    class Delete {

        private CustomerUpdateRequest customerUpdateRequest;

        @BeforeEach
        void init_request() {
            String email = "psy@naver.com";

            customerUpdateRequest = new CustomerUpdateRequest(email);
        }

        @Test
        @Transactional
        void 고객삭제_성공_테스트() {
            // When
            assertDoesNotThrow(() -> customerService.deleteCustomer(customerUpdateRequest));
        }
    }

    @Nested
    class Search {

        private CustomerUpdateRequest searchRequest;

        @BeforeEach
        void init_request() {
            String email = "psy@naver.com";

            searchRequest = new CustomerUpdateRequest(email);
        }

        @Test
        void 고객_상세조회_성공_테스트() {
            // Given
            String mockName = "psy";
            Customer customer = new Customer(UUID.randomUUID(),
                    mockName,
                    searchRequest.email(),
                    LocalDateTime.now());
            CustomerResponse expectCustomer = CustomerResponse.of(customer);

            given(customerRepository.findByEmail(searchRequest.email())).willReturn(Optional.of(customer));

            // When
            CustomerResponse customerResponse = customerService.findDetailCustomer(searchRequest);

            // Then
            assertThat(customerResponse).isNotNull();

            assertAll(
                    () -> assertThat(expectCustomer.getName()).isEqualTo(customerResponse.getName()),
                    () -> assertThat(expectCustomer.getEmail()).isEqualTo(customerResponse.getEmail()),
                    () -> assertThat(expectCustomer.getCreateAt()).isEqualTo(customerResponse.getCreateAt())
            );
        }

        @Test
        void 존재하지_않는_고객조회로_실패_테스트() {
            // Given
            given(customerRepository.findByEmail(searchRequest.email())).willReturn(Optional.empty());

            // When
            assertThatThrownBy(() -> customerService.findDetailCustomer(searchRequest))
                    .isInstanceOf(CustomerException.class);
        }

        @Test
        void 모든_고객조회_성공_테스트() {
            // Given
            Customer mockCustomer = new Customer(UUID.randomUUID(),
                    "mock",
                    "mock@naver.com",
                    LocalDateTime.now());

            given(customerRepository.findAll()).willReturn(List.of(mockCustomer));

            // When
            CustomersResponse customers = customerService.findAllCustomer();

            // Then
            assertThat(customers).isNotNull();
            assertThat(customers.getCustomerResponses()).isNotEmpty();
        }
    }

    @Nested
    class Update {

        private CustomerUpdateRequest updateRequest;

        @BeforeEach
        void init_request() {
            String email = "before@naver.com";
            String newEamil = "after@naver.com";

            updateRequest = new CustomerUpdateRequest(email, newEamil);
        }

        @Test
        @Transactional
        void 고객_이메일_업데이트_성공_테스트() {
            // Given
            String mockName = "mock";
            Customer customer = new Customer(UUID.randomUUID(),
                    mockName,
                    updateRequest.email(),
                    LocalDateTime.now());

            Customer updatedCustomer = new Customer(customer.getCustomerId(),
                    customer.getName(),
                    updateRequest.newEmail(),
                    customer.getCreateAt());

            CustomerResponse expectCustomer = CustomerResponse.of(updatedCustomer);

            given(customerRepository.findByEmail(updateRequest.newEmail())).willReturn(Optional.empty());
            given(customerRepository.findByEmail(updateRequest.email())).willReturn(Optional.of(customer));
            given(customerRepository.update(customer, updateRequest.newEmail())).willReturn(updatedCustomer);

            // When
            CustomerResponse customerResponse = customerService.updateCustomer(updateRequest);

            // Then
            assertThat(customerResponse).isNotNull();

            assertAll(
                    () -> assertThat(expectCustomer.getName()).isEqualTo(customerResponse.getName()),
                    () -> assertThat(expectCustomer.getEmail()).isEqualTo(customerResponse.getEmail()),
                    () -> assertThat(expectCustomer.getCreateAt()).isEqualTo(customerResponse.getCreateAt())
            );
        }
    }
}
