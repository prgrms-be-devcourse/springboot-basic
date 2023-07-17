package com.tangerine.voucher_system.application.customer.controller;

import com.tangerine.voucher_system.application.customer.model.Customer;
import com.tangerine.voucher_system.application.customer.model.Name;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerDtoTest {

    static List<Customer> customers = List.of(
            new Customer(UUID.randomUUID(), new Name("사과"), false),
            new Customer(UUID.randomUUID(), new Name("딸기"), true),
            new Customer(UUID.randomUUID(), new Name("포도"), false),
            new Customer(UUID.randomUUID(), new Name("배"), false)
    );
    static List<CustomerDto> customerDto = List.of(
            new CustomerDto(UUID.randomUUID(), new Name("사과"), false),
            new CustomerDto(UUID.randomUUID(), new Name("딸기"), true),
            new CustomerDto(UUID.randomUUID(), new Name("포도"), false),
            new CustomerDto(UUID.randomUUID(), new Name("배"), false)
    );

    static Stream<Arguments> provideValidCustomers() {
        return customers.stream()
                .map(Arguments::of);
    }

    static Stream<Arguments> provideValidCustomerDto() {
        return customerDto.stream()
                .map(Arguments::of);
    }

    @ParameterizedTest
    @DisplayName("Dto에서 Domain으로 변환하면 성공한다.")
    @MethodSource("provideValidCustomers")
    void of_ParamCustomer_ReturnCustomerDto(Customer customer) {

        CustomerDto dto = CustomerDto.of(customer);

        assertThat(dto.name()).isSameAs(customer.getName());
    }

    @ParameterizedTest
    @DisplayName("Domain 에서 Dto 로 변환하면 성공한다.")
    @MethodSource("provideValidCustomerDto")
    void to(CustomerDto customerDto) {

        Customer entity = customerDto.to();

        assertThat(entity.getCustomerId()).isSameAs(customerDto.customerId());
    }

}