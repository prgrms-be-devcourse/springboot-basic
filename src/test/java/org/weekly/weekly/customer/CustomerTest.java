package org.weekly.weekly.customer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CustomerTest {

    @Test
    void 회원생성_성공_테스트() {
        Customer customer = new Customer(UUID.randomUUID(), "hello", "email@gmail.com");

    }

    @ParameterizedTest
    @ValueSource(strings = {"123", "123@", "213@n", "abc@naver.", "abc@naver.c", "abc@naver.comc"})
    void 회원생성_실패_테스트(String email) {
        assertThatThrownBy(()->Customer.of(UUID.randomUUID(), "hello", email)).isInstanceOf(CustomerException.class);
    }
}
