package org.devcourse.voucher.domain.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

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

}
