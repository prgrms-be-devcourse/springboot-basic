package org.weekly.weekly.customer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.weekly.weekly.customer.domain.Customer;
import org.weekly.weekly.customer.exception.CustomerException;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;

public class CustomerTest {

    @Test
    void 회원생성_성공_테스트() {
        assertThat(Customer.of(UUID.randomUUID(), "hello", "email@gmail.com"), instanceOf(Customer.class));
    }

    @ParameterizedTest
    @ValueSource(strings = {"123", "123@", "213@n", "abc@naver.", "abc@naver.c", "abc@naver.comc"})
    void 회원생성_실패_테스트(String email) {
        assertThatThrownBy(() -> Customer.of(UUID.randomUUID(), "hello", email)).isInstanceOf(CustomerException.class);
    }
}
