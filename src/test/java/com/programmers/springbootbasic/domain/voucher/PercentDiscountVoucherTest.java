package com.programmers.springbootbasic.domain.voucher;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PercentDiscountVoucherTest {
    @Test
    void 정상입력값_바우처생성_성공() {
        // given
        UUID voucherId = UUID.randomUUID();
        VoucherType voucherType = VoucherType.PERCENT;
        String name = "회원가입 30% 할인 쿠폰";
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime expiredAt = createdAt.plusMonths(3);
        Duration duration = new Duration(createdAt, expiredAt);
        int percent = 30;

        // when
        Voucher percentDiscountVoucher = new PercentDiscountVoucher(voucherId, voucherType, name, duration, percent);

        // then
        assertThat(percentDiscountVoucher.getName()).isEqualTo(name);
    }

    @Test
    void 정상입력값최소금액포함_바우처생성_성공() {
        // given
        UUID voucherId = UUID.randomUUID();
        VoucherType voucherType = VoucherType.PERCENT;
        String name = "회원가입 30% 할인 쿠폰";
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime expiredAt = createdAt.plusMonths(3);
        Duration duration = new Duration(createdAt, expiredAt);
        Long minimumPrice = 3_000L;
        int percent = 30;

        // when
        Voucher percentDiscountVoucher = new PercentDiscountVoucher(voucherId, voucherType, name, minimumPrice, duration, percent);

        // then
        assertThat(percentDiscountVoucher.getName()).isEqualTo(name);
    }

    @Test
    void 잘못된퍼센트_바우처생성_예외발생() {
        // given
        UUID voucherId = UUID.randomUUID();
        VoucherType voucherType = VoucherType.PERCENT;
        String name = "회원가입 30% 할인 쿠폰";
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime expiredAt = createdAt.plusMonths(3);
        Duration duration = new Duration(createdAt, expiredAt);
        int percent = 300;

        // when && then
        assertThatThrownBy(() -> new PercentDiscountVoucher(voucherId, voucherType, name, duration, percent))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 잘못된퍼센트최소금액포함_바우처생성_예외발생() {
        // given
        UUID voucherId = UUID.randomUUID();
        VoucherType voucherType = VoucherType.PERCENT;
        String name = "회원가입 30% 할인 쿠폰";
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime expiredAt = createdAt.plusMonths(3);
        Duration duration = new Duration(createdAt, expiredAt);
        Long minimumPrice = 3_000L;
        int percent = 300;

        // when && then
        assertThatThrownBy(() -> new PercentDiscountVoucher(voucherId, voucherType, name, minimumPrice, duration, percent))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @CsvSource(value = {"4550,33,3050", "5500,10,4950"})
    void 물건금액할인퍼센트_할인_할인된금액(Long price, int percent, Long expectedPrice) {
        // given
        Duration duration = new Duration(LocalDateTime.now(), LocalDateTime.MAX);
        VoucherType voucherType = VoucherType.PERCENT;
        PercentDiscountVoucher voucher = new PercentDiscountVoucher(
                UUID.randomUUID(),
                voucherType,
                percent + "% 할인권",
                duration,
                percent);

        // when
        Long discountedPrice = voucher.getDiscountPrice(price);

        // then
        assertThat(discountedPrice).isEqualTo(expectedPrice);
    }
}
