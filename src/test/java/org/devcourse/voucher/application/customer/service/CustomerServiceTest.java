package org.devcourse.voucher.application.customer.service;

import org.devcourse.voucher.application.customer.controller.dto.CustomerResponse;
import org.devcourse.voucher.application.customer.model.Customer;
import org.devcourse.voucher.application.customer.model.Email;
import org.devcourse.voucher.application.customer.repository.CustomerRepository;
import org.devcourse.voucher.core.exception.ErrorType;
import org.devcourse.voucher.core.exception.NotFoundException;
import org.devcourse.voucher.core.exception.stub.CustomerStubs;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static org.devcourse.voucher.core.exception.ErrorType.NOT_FOUND_CUSTOMER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Spy
    @InjectMocks
    CustomerService customerService;

    @Mock
    CustomerRepository customerRepository;

    private final UUID customerId = UUID.randomUUID();

    private final Pageable pageable = Pageable.ofSize(5);

    private static Stream<Arguments> createCustomerArguments() {
        return Stream.of(
                Arguments.arguments("test1", new Email("test1@test.com")),
                Arguments.arguments("test2", new Email("test2@test.com")),
                Arguments.arguments("test3", new Email("test3@test.com")),
                Arguments.arguments("test4", new Email("test4@test.com")),
                Arguments.arguments("test5", new Email("test5@test.com"))
        );
    }

    @ParameterizedTest
    @MethodSource("createCustomerArguments")
    @DisplayName("고객 생성 테스트")
    void createCustomerTest(String name, Email email) {
        // given
        Customer saveCustomer = new Customer(customerId, name, email);
        CustomerResponse response = new CustomerResponse(customerId, name, email.getAddress());

        // when
        doReturn(saveCustomer)
                .when(customerRepository)
                .insert(any(Customer.class));

        // then
        assertThat(customerService.createCustomer(name, email))
                .usingRecursiveComparison()
                .isEqualTo(response);
    }

    @Test
    @DisplayName("페이지를 이용한 고객 조회 테스트")
    void recallAllCustomerTest() {
        // given
        List<Customer> inquiryCustomers = CustomerStubs.customerList();
        List<CustomerResponse> response = CustomerStubs.customerResponseList(inquiryCustomers);
        // when
        doReturn(inquiryCustomers)
                .when(customerRepository)
                .findAll(pageable);

        // then
        assertThat(customerService.recallAllCustomer(pageable))
                .usingRecursiveComparison()
                .isEqualTo(response);
    }

    @Test
    @DisplayName("아이디로 고객 조회 테스트")
    void recallCustomerByIdTest() {
        // given
        Customer inquiryCustomer = CustomerStubs.customer(customerId);
        CustomerResponse response = CustomerStubs.customerResponse(customerId);

        // when
        doReturn(Optional.of(inquiryCustomer))
                .when(customerRepository)
                .findById(customerId);

        // then
        assertThat(customerService.recallCustomerById(customerId))
                .usingRecursiveComparison()
                .isEqualTo(response);
    }

    @Test
    @DisplayName("존재하지 않는 아이디로 바우처 조회 시도시 예외 발생")
    void notValidRecallCustomerByIdTest() {
        // given
        Customer notExistCustomer = CustomerStubs.customer(customerId);

        // when
        doThrow(new NotFoundException(NOT_FOUND_CUSTOMER, customerId))
                .when(customerService)
                .recallCustomerById(customerId);

        // then
        assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> customerService.recallCustomerById(customerId));
    }

    @Test
    @DisplayName("고객 업데이트 테스트")
    void updateCustomerTest() {
        // given
        String updateName = "modify";
        Email updateEmail = new Email("modify@test.com");
        Customer origin = CustomerStubs.customer(customerId);
        Customer updateCustomer = CustomerStubs.customer(customerId);
        updateCustomer.setName(updateName);
        updateCustomer.setEmail(updateEmail);
        CustomerResponse response = CustomerStubs.customerResponse(
                customerId,
                updateCustomer.getName(),
                updateCustomer.getEmail()
        );

        // when
        doReturn(Optional.of(origin))
                .when(customerRepository)
                .findById(customerId);
        doReturn(updateCustomer)
                .when(customerRepository)
                .update(any(Customer.class));

        // then
        assertThat(customerService.updateCustomer(customerId, updateName, updateEmail))
                .usingRecursiveComparison()
                .isEqualTo(response);
    }

    @Test
    @DisplayName("존재하지 않는 고객 업데이트 시도시 예외 발생")
    void notValidUpdateCustomerTest() {
        // given
        String updateName = "modify";
        Email updateEmail = new Email("modify@test.com");
        Customer notExistCustomer = CustomerStubs.customer(customerId);

        // when
        doThrow(new NotFoundException(NOT_FOUND_CUSTOMER, customerId))
                .when(customerService)
                .updateCustomer(customerId, updateName, updateEmail);

        // then
        assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> customerService.updateCustomer(customerId, updateName, updateEmail));
    }

    @Test
    @DisplayName("고객 삭제 테스트")
    void deleteCustomerTest() {
        // when
        doNothing()
                .when(customerRepository)
                .deleteById(customerId);

        // then
        customerService.deleteCustomer(customerId);
        verify(customerRepository).deleteById(customerId);
    }
}