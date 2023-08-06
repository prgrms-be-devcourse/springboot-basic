package com.programmers.springbootbasic.domain.voucher;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FixedAmountVoucherTest {
    @Test
    void 정상입력값_바우처생성_성공() {
        // given
        UUID voucherId = UUID.randomUUID();
        int amount = 5_000;

        // when
        Voucher fixedAmountVoucher = new FixedAmountVoucher(voucherId, amount);

        // then
        assertThat(fixedAmountVoucher).isNotNull();
    }

    @Test
    void 잘못된할인금액_바우처생성_예외발생() {
        // given
        UUID voucherId = UUID.randomUUID();
        int amount = 1_000_000_000;

        // when && then
        assertThatThrownBy(() -> new FixedAmountVoucher(voucherId, amount))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @CsvSource(value = {"10000,5000,5000", "300,50000,0"})
    void 물건금액할인금액_할인_할인된금액(Long price, int amount, Long expectedPrice) {
        // given
        FixedAmountVoucher voucher = new FixedAmountVoucher(
                UUID.randomUUID(),
                amount);

        // when
        Long discountedPrice = voucher.getDiscountPrice(price);

        // then
        assertThat(discountedPrice).isEqualTo(expectedPrice);
    }
}
