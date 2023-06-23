package org.weekly.weekly.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class VoucherMenuTest {
    @ParameterizedTest
    @ValueSource(strings = {"exti", "he lo", "123fdp", "test"})
    void 사용자_입력이_Voucher_메뉴에_없을때_예외반환(String testValue) {
        assertThatThrownBy(VoucherMenu.getMenu(testValue))
                .isInstanceOf(RuntimeException.class);
    }
}
