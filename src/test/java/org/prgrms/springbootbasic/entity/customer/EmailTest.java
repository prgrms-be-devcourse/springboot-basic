package org.prgrms.springbootbasic.entity.customer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.prgrms.springbootbasic.exception.ServiceExceptionMessage.INVALID_EMAIL_FORMAT_EXP_MSG;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.springbootbasic.exception.InvalidEmailFormatException;

class EmailTest {

    @Test
    @DisplayName("이메일 정상 생성")
    void testCreateEmailSuccess() {
        //given
        var stringEmail = "spring@spring.com";

        //when
        var email = new Email(stringEmail);

        //then
        assertAll(
            () -> assertThat(email).isNotNull(),
            () -> assertThat(email.getEmail()).isEqualTo(stringEmail)
        );
    }

    @Test
    @DisplayName("이메일 생성 실패")
    void testCreateEmailFailBecauseInvalidFormat() {
        //given
        var stringEmail = "spring";

        //when
        //then
        assertThatThrownBy(() -> new Email(stringEmail))
            .isInstanceOf(InvalidEmailFormatException.class)
            .hasMessageContaining(INVALID_EMAIL_FORMAT_EXP_MSG.getMessage());
    }
}