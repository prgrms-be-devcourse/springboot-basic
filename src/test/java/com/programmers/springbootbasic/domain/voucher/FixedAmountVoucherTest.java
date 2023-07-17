package com.programmers.springbootbasic.domain.voucher;

import com.programmers.springbootbasic.domain.model.Duration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FixedAmountVoucherTest {
    @Test
    void 정상입력값_바우처생성_성공() {
        // given
        UUID voucherId = UUID.randomUUID();
        VoucherType voucherType = VoucherType.FIX;
        String name = "회원가입 5000원 할인 쿠폰";
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime expiredAt = createdAt.plusMonths(3);
        Long minimumPrice = 3_000L;
        Duration duration = new Duration(createdAt, expiredAt);
        int amount = 5_000;
        boolean used = false;

        // when
        Voucher fixedAmountVoucher = new FixedAmountVoucher(voucherId, voucherType, name, minimumPrice, duration, amount, used);

        // then
        assertThat(fixedAmountVoucher).isNotNull();
    }

    @Test
    void 잘못된할인금액_바우처생성_예외발생() {
        // given
        UUID voucherId = UUID.randomUUID();
        VoucherType voucherType = VoucherType.FIX;
        String name = "회원가입 5000원 할인 쿠폰";
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime expiredAt = createdAt.plusMonths(3);
        Duration duration = new Duration(createdAt, expiredAt);
        Long minimumPrice = 3_000L;
        int amount = 1_000_000_000;
        boolean used = false;

        // when && then
        assertThatThrownBy(() -> new FixedAmountVoucher(voucherId, voucherType, name, minimumPrice, duration, amount, used))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @CsvSource(value = {"10000,5000,5000", "300,50000,0"})
    void 물건금액할인금액_할인_할인된금액(Long price, int amount, Long expectedPrice) {
        // given
        Duration duration = new Duration(LocalDateTime.now(), LocalDateTime.MAX);
        VoucherType voucherType = VoucherType.FIX;
        FixedAmountVoucher voucher = new FixedAmountVoucher(
                UUID.randomUUID(),
                voucherType,
                amount + "원 할인",
                0L,
                duration,
                amount,
                false);

        // when
        Long discountedPrice = voucher.getDiscountPrice(price);

        // then
        assertThat(discountedPrice).isEqualTo(expectedPrice);
    }
}
