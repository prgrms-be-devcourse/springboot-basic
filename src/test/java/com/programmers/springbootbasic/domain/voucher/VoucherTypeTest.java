package com.programmers.springbootbasic.domain.voucher;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class VoucherTypeTest {
    @Test
    void 정률할인_바우처타입으로변환_성공() {
        // given
        String description = VoucherType.PERCENT.getDescription();

        // when
        VoucherType voucherType = VoucherType.from(description);

        // then
        assertThat(voucherType).isEqualTo(VoucherType.PERCENT);
    }

    @Test
    void 정액할인_바우처타입으로변환_성공() {
        // given
        String description = VoucherType.FIX.getDescription();

        // when
        VoucherType voucherType = VoucherType.from(description);

        // then
        assertThat(voucherType).isEqualTo(VoucherType.FIX);
    }

    @Test
    void 잘못된값_바우처타입으로변환_에외발생() {
        // given
        String description = "뭔지 모름";

        // when && then
        assertThatThrownBy(() -> VoucherType.from(description))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
