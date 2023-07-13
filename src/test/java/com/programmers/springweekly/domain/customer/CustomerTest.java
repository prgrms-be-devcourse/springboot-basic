package com.programmers.springweekly.domain.customer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


public class CustomerTest {

    @Test
    @DisplayName("고객을 생성할 수 있다.")
    void createCustomer() {
        // given && when
        Customer customer = Customer.builder()
                .customerId(UUID.randomUUID())
                .customerName("changhyeon")
                .customerEmail("changhyeon.h@kakao.com")
                .customerType(CustomerType.BLACKLIST)
                .build();

        // then
        assertThat(customer).isInstanceOf(Customer.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"diamond", "silver", "bronze"})
    @DisplayName("고객 타입에 없는 타입이 입력되면 예외를 발생시킨다.")
    void customerTypeTest(String input) {
        assertThatThrownBy(() -> CustomerType.from(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Input: " + input + ", 찾으시는 고객 타입이 없습니다.");
    }

}
