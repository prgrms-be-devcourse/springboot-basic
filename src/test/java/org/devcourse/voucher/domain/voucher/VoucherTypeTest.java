package org.devcourse.voucher.domain.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class VoucherTypeTest {

    @ParameterizedTest
    @ValueSource(strings = {"fix", "Fix", "FIX", "fiX"})
    @DisplayName("사용자 입력에 맞게 바우처 타입을 찾는데 성공한다")
    void successFindVoucherTypeFix(String type) {
        VoucherType foundVoucherType = VoucherType.find(type);

        assertThat(foundVoucherType).isEqualTo(VoucherType.FIX);
    }

    @ParameterizedTest
    @ValueSource(strings = {"percent", "PERCENT", "percenT", "perCent"})
    @DisplayName("사용자 입력에 맞게 바우처 타입을 찾는데 성공한다")
    void successFindVoucherTypePercent(String type) {
        VoucherType foundVoucherType = VoucherType.find(type);

        assertThat(foundVoucherType).isEqualTo(VoucherType.PERCENT);
    }

    @ParameterizedTest
    @ValueSource(strings = {"point", "perceent", "fixx", "fiiix", "money"})
    @DisplayName("제공되지 않은 바우처 타입을 입력해 실패한다")
    void failFindVoucherTypePercent(String type) {
        assertThatThrownBy(() -> VoucherType.find(type))
                .isExactlyInstanceOf(RuntimeException.class)
                .hasMessage("사용 불가능한 바우처 타입입니다");
    }
}
