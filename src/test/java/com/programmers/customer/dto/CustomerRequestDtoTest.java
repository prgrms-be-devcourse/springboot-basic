package com.programmers.customer.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

class CustomerRequestDtoTest {

    @DisplayName("요청 클래스의 고객 ID로 유효하지 않은 인자가 오는 경우 에러를 반환한다.")
    @Test
    void validateRequestDtoCustomerIdTest() {
        assertThatThrownBy(() -> new CustomerRequestDto(null, "weonest"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
