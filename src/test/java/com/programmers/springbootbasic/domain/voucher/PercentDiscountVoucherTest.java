package com.programmers.springbootbasic.domain.voucher;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PercentDiscountVoucherTest {
    @Test
    void 정상입력값_바우처생성_성공() {
        // given
        UUID voucherId = UUID.randomUUID();
        VoucherType voucherType = VoucherType.PERCENT;
        int percent = 30;

        // when
        Voucher percentDiscountVoucher = new PercentDiscountVoucher(voucherId, voucherType, percent);

        // then
        assertThat(percentDiscountVoucher).isNotNull();
    }

    @Test
    void 잘못된퍼센트_바우처생성_예외발생() {
        // given
        UUID voucherId = UUID.randomUUID();
        VoucherType voucherType = VoucherType.PERCENT;
        int percent = 300;

        // when && then
        assertThatThrownBy(() -> new PercentDiscountVoucher(voucherId, voucherType, percent))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @CsvSource(value = {"4550,33,3050", "5500,10,4950"})
    void 물건금액할인퍼센트_할인_할인된금액(Long price, int percent, Long expectedPrice) {
        // given
        VoucherType voucherType = VoucherType.PERCENT;
        PercentDiscountVoucher voucher = new PercentDiscountVoucher(
                UUID.randomUUID(),
                voucherType,
                percent
        );

        // when
        Long discountedPrice = voucher.getDiscountPrice(price);

        // then
        assertThat(discountedPrice).isEqualTo(expectedPrice);
    }
}
