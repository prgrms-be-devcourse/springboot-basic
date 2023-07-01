package com.devcourse.springbootbasic.application.domain.customer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.text.MessageFormat;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    @ParameterizedTest
    @DisplayName("도메인 출력 테스트")
    @MethodSource("provideCustomers")
    void toStringTest(int index, String name, Customer customer) {
        var expected = MessageFormat.format("Customer(id: {0}, name : {1})", index, name);
        var result = customer.toString();
        assertEquals(expected, result);
    }

    static Stream<Arguments> provideCustomers() {
        return Stream.of(
                Arguments.of(0, "사과", new Customer(0, "사과")),
                Arguments.of(1, "딸기", new Customer(1, "딸기")),
                Arguments.of(2, "포도", new Customer(2, "포도")),
                Arguments.of(3, "배", new Customer(3, "배"))
        );
    }

}