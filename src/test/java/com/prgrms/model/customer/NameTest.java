package com.prgrms.model.customer;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class NameTest {

    private final String nullName = null;
    private final String blankName =" ";
    private final String emptyName ="";

    @Test
    @DisplayName("이름이 NULL로 들어오는 경우 예외를 던진다.")
    void customer_NullName_throwsException() {
        //when_then
        assertThatThrownBy(() -> new Name(nullName))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("이름이 공백있는 빈 문자열로 들어오는 경우 예외를 던진다.")
    void customer_BlankName_throwsException() {
        //when_then
        assertThatThrownBy(() -> new Name(blankName))
                .isInstanceOf(IllegalArgumentException.class);
    }


    @Test
    @DisplayName("이름이 공백이 없는 빈 문자열로 들어오는 경우 예외를 던진다.")
    void customer_EmptyName_throwsException() {
        //when_then
        assertThatThrownBy(() -> new Name( emptyName))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
