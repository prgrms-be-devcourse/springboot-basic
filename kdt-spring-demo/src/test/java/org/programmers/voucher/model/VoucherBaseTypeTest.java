package org.programmers.voucher.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VoucherBaseTypeTest {

    @Test
    @DisplayName("바우처 타입에 문자열을 전달하면 해당하는 Enum을 반환할 수 있다")
    void getInputType() {
        String fixed = "fixed";
        String percent = "percent";
        Assertions.assertThat(VoucherType.getInputType(fixed)).isEqualTo(VoucherType.FIXED);
        Assertions.assertThat(VoucherType.getInputType(percent)).isEqualTo(VoucherType.PERCENT);

    }

    @Test
    @DisplayName("바우처 타입에 올바르지 않은 문자열을 전달하면 예외처리가 발생한다.")
    void getInputTypeException() {
        String fixedError = "FixedError";
        String percentError = "PercentError";

        assertThrows(RuntimeException.class, () -> VoucherType.getInputType(fixedError));
        assertThrows(RuntimeException.class, () -> VoucherType.getInputType(percentError));
    }
}