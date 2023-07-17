package com.prgrms.model.customer;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CustomerTest {

    private final int customerId = 1;
    private final String email = "1234@nan";
    private final Name nullName = new Name(null);
    private final Name blankName = new Name(" ");
    private final Name emptyName = new Name("");

    @Test
    @DisplayName("이름이 NULL로 들어오는 경우 예외를 던진다.")
    void customer_NullName_throwsException() {
        //given
        LocalDateTime createdAt = LocalDateTime.now();

        //when_then
        assertThatThrownBy(() -> new Customer(customerId, nullName, email, createdAt))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("이름이 공백있는 빈 문자열로 들어오는 경우 예외를 던진다.")
    void customer_BlankName_throwsException() {
        //given
        LocalDateTime createdAt = LocalDateTime.now();

        //when_then
        assertThatThrownBy(() -> new Customer(customerId, blankName, email, createdAt))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("이름이 공백이 없는 빈 문자열로 들어오는 경우 예외를 던진다.")
    void customer_EmptyName_throwsException() {
        //given
        LocalDateTime createdAt = LocalDateTime.now();

        //when_then
        assertThatThrownBy(() -> new Customer(customerId, emptyName, email, createdAt))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
