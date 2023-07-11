package com.devcourse.springbootbasic.application.customer.controller;

import com.devcourse.springbootbasic.application.customer.model.Customer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class CustomerDtoTest {

    @ParameterizedTest
    @DisplayName("Dto에서 Domain으로 변환하면 성공한다.")
    @MethodSource("provideValidCustomers")
    void of_ParamCustomer_ReturnCustomerDto(Customer customer) {
        var dto = CustomerDto.of(customer);
        assertThat(dto, instanceOf(CustomerDto.class));
        assertThat(dto.name(), is(customer.getName()));
    }

    @ParameterizedTest
    @DisplayName("Domain에서 Dto로 변환하면 성공한다.")
    @MethodSource("provideValidCustomerDtos")
    void to(CustomerDto customerDto) {
        var entity = CustomerDto.to(customerDto);
        assertThat(entity, instanceOf(Customer.class));
    }

    static Stream<Arguments> provideValidCustomers() {
        return customers.stream()
                .map(Arguments::of);
    }

    static Stream<Arguments> provideValidCustomerDtos() {
        return customerDto.stream()
                .map(Arguments::of);
    }

    static List<Customer> customers = List.of(
            new Customer(UUID.randomUUID(), "사과"),
            new Customer(UUID.randomUUID(), "딸기"),
            new Customer(UUID.randomUUID(), "포도"),
            new Customer(UUID.randomUUID(), "배")
    );

    static List<CustomerDto> customerDto = List.of(
            new CustomerDto(UUID.randomUUID(), "사과"),
            new CustomerDto(UUID.randomUUID(), "딸기"),
            new CustomerDto(UUID.randomUUID(), "포도"),
            new CustomerDto(UUID.randomUUID(), "배")
    );

}