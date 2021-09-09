package org.programmers.console;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;

class ModeInputTypeTest {

    @Test
    @DisplayName("문자열을 입력하면 해당하는 enum이 반환됩니다.")
    void getModeInputType() {
        String exit = "exit";
        String voucher = "voucher";
        String customer = "customer";

        Assertions.assertThat(ModeInputType.getModeInputType(exit)).isEqualTo(ModeInputType.EXIT);
        Assertions.assertThat(ModeInputType.getModeInputType(voucher)).isEqualTo(ModeInputType.VOUCHER);
        Assertions.assertThat(ModeInputType.getModeInputType(customer)).isEqualTo(ModeInputType.CUSTOMER);
    }

    @Test
    @DisplayName("잘못된 문자열이 입력되면 예외처리됩니다.")
    void getModeInputTypeException() {
        assertThrows(RuntimeException.class, () -> ModeInputType.getModeInputType("error"));
    }
}