package com.devcourse.springbootbasic.application.customer.controller;

import com.devcourse.springbootbasic.application.customer.model.Customer;
import com.devcourse.springbootbasic.application.customer.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@SpringJUnitConfig
class CustomerControllerTest {

    static List<Customer> validCustomers = List.of(
            new Customer(UUID.randomUUID(), "사과", false),
            new Customer(UUID.randomUUID(), "딸기", true),
            new Customer(UUID.randomUUID(), "포도", false),
            new Customer(UUID.randomUUID(), "배", false)
    );
    static List<CustomerDto> validCustomerDto = List.of(
            new CustomerDto(UUID.randomUUID(), "사과", false),
            new CustomerDto(UUID.randomUUID(), "딸기", true),
            new CustomerDto(UUID.randomUUID(), "포도", false),
            new CustomerDto(UUID.randomUUID(), "배", false)
    );
    CustomerController controller;
    CustomerService service;

    static Stream<Arguments> provideValidCustomers() {
        return validCustomers.stream()
                .map(Arguments::of);
    }

    static Stream<Arguments> provideValidCustomerDto() {
        return validCustomerDto.stream()
                .map(Arguments::of);
    }

    @BeforeEach
    void init() {
        service = mock(CustomerService.class);
        controller = new CustomerController(service);
    }

    @Test
    @DisplayName("진상고객 정보를 리스트로 반환하면 성공한다.")
    void findBlackCustomers_ParamVoid_ReturnCustomerDtoList() {
        given(service.findAllCustomers()).willReturn(validCustomers);

        List<CustomerDto> result = controller.customerList();

        assertThat(result).isNotEmpty();
        assertThat(result.get(0)).isInstanceOf(CustomerDto.class);
        assertThat(result.get(0).customerId()).isSameAs(validCustomers.get(0).getCustomerId());
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 고객을 추가하면 성공한다.")
    @MethodSource("provideValidCustomerDto")
    void registerCustomer_ParamCustomer_InsertAndReturnCustomerDto(CustomerDto customerDto) {
        given(service.registCustomer(any())).willReturn(CustomerDto.to(customerDto));

        CustomerDto insertedCustomer = controller.registerCustomer(customerDto);

        assertThat(insertedCustomer).isNotNull();
        assertThat(insertedCustomer).isInstanceOf(CustomerDto.class);
        assertThat(insertedCustomer.name()).isEqualTo(customerDto.name());
    }

    @ParameterizedTest
    @DisplayName("존재하는 고객을 업데이트하면 성공한다.")
    @MethodSource("provideValidCustomers")
    void updateCustomer_ParamExistCustomerDto_UpdateCustomerDto(Customer customer) {
        given(service.updateCustomer(any())).willReturn(customer);

        CustomerDto updatedCustomer = controller.updateCustomer(CustomerDto.of(customer));

        assertThat(updatedCustomer.customerId()).isSameAs(customer.getCustomerId());
    }

    @Test
    @DisplayName("모든 고객을 반환하면 성공한다.")
    void findAllCustomers_ParamVoid_ReturnCustomerDtoList() {
        given(service.findAllCustomers()).willReturn(validCustomers);

        List<CustomerDto> list = controller.customerList();

        assertThat(list).isNotEmpty();
        assertThat(list.get(0).customerId()).isEqualTo(validCustomers.get(0).getCustomerId());
    }

    @ParameterizedTest
    @DisplayName("존재하는 고객을 아이디로 조회 시 성공한다.")
    @MethodSource("provideValidCustomerDto")
    void findCustomerById_ParamExistCustomerDto_ReturnCustomerDto(CustomerDto customerDto) {
        given(service.findCustomerById(any())).willReturn(CustomerDto.to(customerDto));

        CustomerDto foundCustomer = controller.findCustomerById(customerDto.customerId());

        assertThat(foundCustomer.customerId()).isEqualTo(customerDto.customerId());
    }

    @ParameterizedTest
    @DisplayName("존재하는 고객을 이름으로 조회 시 성공한다.")
    @MethodSource("provideValidCustomerDto")
    void findCustomerByName_ParamExistCustomerDto_ReturnCustomerDto(CustomerDto customerDto) {
        given(service.findCustomerByName(any())).willReturn(CustomerDto.to(customerDto));

        CustomerDto foundCustomer = controller.findCustomerByName(customerDto.name());

        assertThat(foundCustomer.customerId()).isEqualTo(customerDto.customerId());
    }

    @ParameterizedTest
    @DisplayName("존재하는 고객을 아이디로 제거하면 성공한다.")
    @MethodSource("provideValidCustomerDto")
    void deleteCustomerById_ParamExistCustomer_ReturnAndDeleteCustomer(CustomerDto customerDto) {
        given(service.deleteCustomerById(any())).willReturn(CustomerDto.to(customerDto));

        CustomerDto deletedCustomer = controller.unregisterCustomerById(customerDto.customerId());

        assertThat(deletedCustomer.customerId()).isEqualTo(customerDto.customerId());
    }

}