package com.devcourse.springbootbasic.application.customer.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.text.MessageFormat;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerTest {

    static Stream<Arguments> provideCustomers() {
        return Stream.of(
                Arguments.of(
                        UUID.fromString("061d89ad-1a6a-11ee-aed4-0242ac110002"), "사과", false,
                        new Customer(UUID.fromString("061d89ad-1a6a-11ee-aed4-0242ac110002"), "사과", false)),
                Arguments.of(UUID.fromString("06201b27-1a6a-11ee-aed4-0242ac110002"), "딸기", true,
                        new Customer(UUID.fromString("06201b27-1a6a-11ee-aed4-0242ac110002"), "딸기", true)),
                Arguments.of(UUID.fromString("06223606-1a6a-11ee-aed4-0242ac110002"), "포도", false,
                        new Customer(UUID.fromString("06223606-1a6a-11ee-aed4-0242ac110002"), "포도", false)),
                Arguments.of(UUID.fromString("06223606-1a6a-11ee-aed4-0242ac110003"), "배", false,
                        new Customer(UUID.fromString("06223606-1a6a-11ee-aed4-0242ac110003"), "배", false))
        );
    }

    @ParameterizedTest
    @DisplayName("고객 정보가 출력되면 성공한다.")
    @MethodSource("provideCustomers")
    void toString_Customer_ReturnCustomerString(UUID customerId, String name, boolean isBlack, Customer customer) {
        String expected = MessageFormat.format("Customer(id: {0}, name: {1}, isBlack: {2})", customerId, name, isBlack);

        String result = customer.toString();

        assertEquals(expected, result);
    }

}