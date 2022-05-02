package org.prgrms.springbootbasic.entity.customer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.prgrms.springbootbasic.exception.ServiceExceptionMessage.INVALID_NAME_FORMAT_EXP_MSG;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.springbootbasic.exception.InvalidNameFormatException;

class NameTest {

    @Test
    @DisplayName("이름 정상 생성")
    void testCreateNameSuccess() {
        //given
        String stringName = "test";

        //when
        var name = new Name(stringName);

        //then
        assertThat(name).isNotNull();
    }

    @Test
    @DisplayName("이름 생성 실패")
    void testCreateNameFailBecauseInvalidFormat() {
        //given
        String stringName = "this name is too long";

        //when
        //then
        assertThatThrownBy(() -> new Name(stringName))
            .isInstanceOf(InvalidNameFormatException.class)
            .hasMessageContaining(INVALID_NAME_FORMAT_EXP_MSG.getMessage());
    }
}