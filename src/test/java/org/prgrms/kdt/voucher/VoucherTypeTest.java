package org.prgrms.kdt.voucher;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class VoucherTypeTest {

    @DisplayName("문자열로 FIXED 타입 바우처를 만들 수 있다.")
    @ParameterizedTest
    @CsvSource(value = {"fixed", "fiXed", "Fixed"})
    void ofFIXEDTest(String type) {
        // given
        VoucherType voucherType = VoucherType.FIXED;

        // when
        VoucherType actual = VoucherType.of(type);

        // then
        Assertions.assertThat(actual)
                .isEqualTo(voucherType);

    }

    @DisplayName("문자열로 PERCENT 타입 바우처를 만들 수 있다.")
    @ParameterizedTest
    @CsvSource(value = {"Percent", "perCent", "percent"})
    void ofPERCENTTest(String type) {
        // given
        VoucherType voucherType = VoucherType.PERCENT;

        // when
        VoucherType actual = VoucherType.of(type);

        // then
        Assertions.assertThat(actual)
                .isEqualTo(voucherType);
    }

    @DisplayName("등록되지 않은 문자열로 바우처를 만들 수 없다.")
    @ParameterizedTest
    @CsvSource(value = {"123", "fix", "default"})
    void ofTestIllegalArgumentException(String type) {
        // given
        VoucherType voucherType = VoucherType.PERCENT;

        // when

        // then
        assertThrows(IllegalArgumentException.class, () -> VoucherType.of(type));
    }
}