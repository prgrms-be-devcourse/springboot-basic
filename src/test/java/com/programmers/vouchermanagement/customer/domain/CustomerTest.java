package com.programmers.vouchermanagement.customer.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;

class CustomerTest {
    @DisplayName("🚨 고객 이름이 빈칸(or null)이거나 20자가 넘으면 고객이 생성되지 않는다.")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"", " ", "123456789012345678901"})
    void customerNameNullBlankOver20(String input) {
        assertThrows(IllegalArgumentException.class, () -> {
            new Customer(UUID.randomUUID(), input, true);
        });
    }

}