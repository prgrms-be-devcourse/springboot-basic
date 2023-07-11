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
        String name = "회원가입 30% 할인 쿠폰";
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime expiredAt = createdAt.plusMonths(3);
        VoucherDateTime voucherDateTime = new VoucherDateTime(createdAt, expiredAt);
        int percent = 30;

        // when
        Voucher percentDiscountVoucher = new PercentDiscountVoucher(voucherId, name, voucherDateTime, percent);

        // then
        assertThat(percentDiscountVoucher.getName()).isEqualTo(name);
    }

    @Test
    void 정상입력값최소금액포함_바우처생성_성공() {
        // given
        UUID voucherId = UUID.randomUUID();
        String name = "회원가입 30% 할인 쿠폰";
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime expiredAt = createdAt.plusMonths(3);
        VoucherDateTime voucherDateTime = new VoucherDateTime(createdAt, expiredAt);
        Long minimumPrice = 3_000L;
        int percent = 30;

        // when
        Voucher percentDiscountVoucher = new PercentDiscountVoucher(voucherId, name, minimumPrice, voucherDateTime, percent);

        // then
        assertThat(percentDiscountVoucher.getName()).isEqualTo(name);
    }

    @Test
    void 잘못된퍼센트_바우처생성_예외발생() {
        // given
        UUID voucherId = UUID.randomUUID();
        String name = "회원가입 30% 할인 쿠폰";
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime expiredAt = createdAt.plusMonths(3);
        VoucherDateTime voucherDateTime = new VoucherDateTime(createdAt, expiredAt);
        int percent = 300;

        // when && then
        assertThatThrownBy(() -> new PercentDiscountVoucher(voucherId, name, voucherDateTime, percent))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 잘못된퍼센트최소금액포함_바우처생성_예외발생() {
        // given
        UUID voucherId = UUID.randomUUID();
        String name = "회원가입 30% 할인 쿠폰";
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime expiredAt = createdAt.plusMonths(3);
        VoucherDateTime voucherDateTime = new VoucherDateTime(createdAt, expiredAt);
        Long minimumPrice = 3_000L;
        int percent = 300;

        // when && then
        assertThatThrownBy(() -> new PercentDiscountVoucher(voucherId, name, minimumPrice, voucherDateTime, percent))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @CsvSource(value = {"4550,33,3050", "5500,10,4950"})
    void 물건금액할인퍼센트_할인_할인된금액(Long price, int percent, Long expectedPrice) {
        VoucherDateTime voucherDateTime = new VoucherDateTime(LocalDateTime.now(), LocalDateTime.MAX);
        // given
        PercentDiscountVoucher voucher = new PercentDiscountVoucher(
                UUID.randomUUID(),
                percent + "% 할인권",
                voucherDateTime,
                percent);

        // when
        Long discountedPrice = voucher.getDiscountPrice(price);

        // then
        assertThat(discountedPrice).isEqualTo(expectedPrice);
    }
}
