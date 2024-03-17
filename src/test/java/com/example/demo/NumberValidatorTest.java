package com.example.demo;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.demo.enums.VoucherDiscountType;
import com.example.demo.view.validate.NumberValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class NumberValidatorTest {

    private static final String ERROR_MESSAGE_NOT_POSITIVE_INTEGER_VALUE = "[ERROR] 1이상의 숫자를 입력해주세요.";
    private static final String ERROR_MESSAGE_NOT_PERCENT_VALUE = "[ERROR] 1이상 ~ 100 이하의 숫자를 입력해주세요.";


    @DisplayName("정액 할인 금액 validation 실패 테스트")
    @ParameterizedTest
    @ValueSource(strings = {"0", "-365", "-593"})
    void 정액_할인액_검증_실패_테스트(String source) {
        assertThatThrownBy(() -> NumberValidator.validateAmount(VoucherDiscountType.FIX, source))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ERROR_MESSAGE_NOT_POSITIVE_INTEGER_VALUE);
    }

    @DisplayName("정률 할인 금액 validation 실패 테스트1 : 양수가 아닌 입력이 들어오는 경우")
    @ParameterizedTest
    @ValueSource(strings = {"-1", "0", "-332", "-593"})
    void 정률_할인액_검증_실패_테스트1(String source) {
        assertThatThrownBy(() -> NumberValidator.validateAmount(VoucherDiscountType.PERCENT, source))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ERROR_MESSAGE_NOT_POSITIVE_INTEGER_VALUE);
    }

    @DisplayName("정률 할인 금액 validation 실패 테스트2 : 1 ~ 100 범위를 벗어나는 값이 들어오는 경우")
    @ParameterizedTest
    @ValueSource(strings = {"101", "200", "1000"})
    void 정률_할인액_검증_실패_테스트2(String source) {
        assertThatThrownBy(() -> NumberValidator.validateAmount(VoucherDiscountType.PERCENT, source))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ERROR_MESSAGE_NOT_PERCENT_VALUE);
    }

    @DisplayName("정액 할인 금액 validation 성공 테스트")
    @ParameterizedTest
    @ValueSource(strings = {"1", "365", "13593"})
    void 정액_할인액_검증_성공_테스트(String source) {
        assertThatNoException().isThrownBy(() -> NumberValidator.validateAmount(VoucherDiscountType.FIX, source));
    }

    @DisplayName("정율 할인 금액 validation 성공 테스트")
    @ParameterizedTest
    @ValueSource(strings = {"1", "100", "50"})
    void 정율_할인액_검증_성공_테스트(String source) {
        assertThatNoException().isThrownBy(() -> NumberValidator.validateAmount(VoucherDiscountType.PERCENT, source));
    }
}

