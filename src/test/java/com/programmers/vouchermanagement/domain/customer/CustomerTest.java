package com.programmers.vouchermanagement.domain.customer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CustomerTest {

    @Test
    @DisplayName("email이 이메일 형식이 아니면 예외가 발생한다.")
    void create_invalidEamil_fail() {
        assertThatThrownBy(() -> new Customer("test"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid email format");
    }
}
