package org.prgrms.kdt.domain.customer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.error.Message;
import org.prgrms.kdt.error.ValidationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class EmailTest {

    @Test
    @DisplayName("이메일을 생성할 수 있다.")
    void create() {
        final String emailSource = "suhyunsim@gmail.com";

        final Email email = Email.valueOf(emailSource);

        assertAll(
                () -> assertThat(email).isEqualTo(new Email(emailSource)),
                () -> assertThat(email.email()).isEqualTo(emailSource)
        );
    }

    @Test
    @DisplayName("이메일 생성자 예외처리")
    void createFail() {
        assertThatThrownBy(() -> Email.valueOf("suhyun"))
                .isInstanceOf(ValidationException.class)
                .hasMessage(Message.WRONG_FORMAT_MESSAGE);
    }

}
