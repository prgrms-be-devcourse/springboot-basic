package org.prgrms.kdt.domain.customer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.error.Message;
import org.prgrms.kdt.error.ValidationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class NameTest {

    @Test
    @DisplayName("이름을 생성할 수 있다.")
    void create() {
        final String nameSource = "심수현";

        final Name name = Name.valueOf(nameSource);

        assertAll(
                () -> assertThat(name).isEqualTo(new Name(nameSource)),
                () -> assertThat(name.name()).isEqualTo(nameSource)
        );
    }

    @Test
    @DisplayName("이름 생성자 예외처리")
    void createFail() {
        assertThatThrownBy(() -> Name.valueOf("심수현123"))
                .isInstanceOf(ValidationException.class)
                .hasMessage(Message.WRONG_FORMAT_MESSAGE);
    }
}
