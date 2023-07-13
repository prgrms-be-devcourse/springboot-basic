package com.prgrms.model.customer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CustomerTest {

    private final int CUSTOMER_ID = 1;
    private final String EMAIL = "1234@nan";
    private final String NULL_NAME = null;
    private final String BLANK_NAME = " ";
    private final String EMPTY_NAME = "";

    @Test
    @DisplayName("이름이 NULL로 들어오는 경우 예외를 던진다.")
    void customer_NullName_throwsException() {
        //given
        LocalDateTime createdAt = LocalDateTime.now();

        //when_then
        assertThatThrownBy(() -> new Customer(CUSTOMER_ID, NULL_NAME, EMAIL, createdAt))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("이름이 공백있는 빈 문자열로 들어오는 경우 예외를 던진다.")
    void customer_BlankName_throwsException() {
        //given
        LocalDateTime createdAt = LocalDateTime.now();

        //when_then
        assertThatThrownBy(() -> new Customer(CUSTOMER_ID, BLANK_NAME, EMAIL, createdAt))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("이름이 공백이 없는 빈 문자열로 들어오는 경우 예외를 던진다.")
    void customer_EmptyName_throwsException() {
        //given
        LocalDateTime createdAt = LocalDateTime.now();

        //when_then
        assertThatThrownBy(() -> new Customer(CUSTOMER_ID, EMPTY_NAME, EMAIL, createdAt))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
