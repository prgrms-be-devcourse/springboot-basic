package org.programmers.kdtspring.entity.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class EmailTest {

    @Test
    @DisplayName("이메일 생성 성공")
    void testCreateEmail() {
        String emailInput = "iop1996@naver.com";

        Email email = new Email(emailInput);

        assertAll(
                () -> assertThat(email).isNotNull(),
                () -> assertThat(email.getEmail()).isEqualTo(emailInput)
        );
    }

    @Test
    @DisplayName("이메일 생성 실패")
    void testCreateEmailFailed() {
        String emailInput = "iop1996@gr";

        assertThatThrownBy(
                () -> new Email(emailInput))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이메일 형식이 맞지 않습니다.");
    }
}