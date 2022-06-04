package org.devcourse.voucher.application.customer.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class CustomerTest {

    private final UUID customerId = UUID.randomUUID();
    private final String name = "test";
    private final Email email = new Email("test@test.com");

    @Test
    @DisplayName("name이 null일 때 예외 발생")
    void nameNullTest() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Customer(customerId, null, email));
    }

    @Test
    @DisplayName("name의 길이가 1 ~ 25 범위를 벗어난 경우 예외 발생")
    void nameRangeTest() {
        String longName = "abcdefghijklmnopqrstuvwxyz";
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Customer(customerId, longName, email));
    }

    @Test
    @DisplayName("id가 null일 때 예외 발생")
    void customerIdNullTest() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Customer(null, name, email));
    }

    @Test
    @DisplayName("email이 null일 때 예외 발생")
    void emailNullTest() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Customer(customerId, name, null));
    }
}