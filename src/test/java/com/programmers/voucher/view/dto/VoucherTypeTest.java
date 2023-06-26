package com.programmers.voucher.view.dto;

import com.programmers.voucher.exception.InvalidCommandException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class VoucherTypeTest {
    @ParameterizedTest
    @DisplayName("올바른 바우처 타입인 경우 성공한다.")
    @ValueSource(strings = {"fixed", "percent"})
    void 올바른_바우처_타입(String name) {
        assertThatCode(() -> VoucherType.findByName(name))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @DisplayName("잘못된 바우처 타입인 경우 예외가 발생한다.")
    @ValueSource(strings = {"a", "123", ""})
    void 잘못된_바우처_타입(String name) {
        assertThatThrownBy(() -> VoucherType.findByName(name))
                .isInstanceOf(InvalidCommandException.class)
                .hasMessage("존재하지 않는 명령어입니다.");
    }
}
