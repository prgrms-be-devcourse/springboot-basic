package org.prgms.voucherProgram.domain.customer.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class EmailTest {

    @DisplayName("이메일이 50자를 넘으면 예외를 발생한다.")
    @Test
    void should_ThrowException_When_EmailLengthOverThan50() {
        String email = "sksmsdixndxnddlrhfahreowksdfasdsadasdsadsadsadasdsadsadsadasd@gmail.com";

        assertThatThrownBy(() -> new Email(email))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("[ERROR] 이메일은 50자 이상을 넘을 수 없습니다.");
    }

    @DisplayName("이메일이 비어있으면 예외를 발생한다.")
    @ParameterizedTest
    @NullAndEmptySource
    void should_ThrowException_When_EmailIsBlank(String email) {
        assertThatThrownBy(() -> new Email(email))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("[ERROR] 이메일이 비어있습니다.");
    }

    @DisplayName("잘못된 이메일 형식이면 예외를 발생한다.")
    @Test
    void should_ThrowException_When_EmailIsWrong() {
        assertThatThrownBy(() -> new Email("sdfasdas@naversadasd"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("[ERROR] 이메일 형식이 잘못되었습니다.");
    }
}
