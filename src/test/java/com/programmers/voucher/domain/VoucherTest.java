package com.programmers.voucher.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class VoucherTest {

    @DisplayName("올바른 바우처 ID, Discount, 생성일을 인자로 바우처를 생성할 수 있다.")
    @Test
    void voucherCreationTest() {
        UUID voucherId = UUID.randomUUID();
        Discount discount = new FixedDiscount(100);
        LocalDateTime createdAt = LocalDateTime.now();

        Voucher createdVoucher = new Voucher(voucherId, discount, createdAt);

        assertThat(createdVoucher).isNotNull();
    }

    @DisplayName("올바른 바우처 ID, Discount, 생성일, 만료일을 인자로 바우처를 생성할 수 있다.")
    @Test
    void voucherCreationWithExpirationTest() {
        UUID voucherId = UUID.randomUUID();
        Discount discount = new FixedDiscount(100);
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime expiredAt = createdAt.plusDays(7);

        Voucher createdVoucher = new Voucher(voucherId, discount, createdAt, expiredAt);

        assertThat(createdVoucher).isNotNull();
    }

    @DisplayName("만료일을 지정하지 않은 바우처의 기본 만료일은 7일이다")
    @Test
    void checkBasicConfigAboutVoucherExpirationTest() {
        UUID voucherId = UUID.randomUUID();
        Discount discount = new FixedDiscount(100);
        LocalDateTime createdAt = LocalDateTime.now();

        Voucher createdVoucher = new Voucher(voucherId, discount, createdAt);
        LocalDateTime expiredAt = createdAt.plusDays(7);

        assertThat(expiredAt).isEqualTo(createdVoucher.getExpiredAt());
    }

    @DisplayName("만료일이 지난 바우처는 사용이 불가능 하다")
    @Test
    void cannotUseExpiredVoucherTest() {
        UUID voucherId = UUID.randomUUID();
        Discount discount = new FixedDiscount(100);
        LocalDateTime createdAt = LocalDateTime.of(1998, 07, 29, 00, 00);
        LocalDateTime expiredAt = LocalDateTime.now().minusDays(1);
        Voucher expiredVoucher = new Voucher(voucherId, discount, createdAt, expiredAt);

        LocalDateTime usedAt = LocalDateTime.now();

        assertThatThrownBy(() -> expiredVoucher.discountWith(50, usedAt))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("유효기간이 유효한 바우처는 사용이 가능하다")
    @Test
    void canUseValidVoucherTest() {
        UUID voucherId = UUID.randomUUID();
        Discount discount = new FixedDiscount(100);
        LocalDateTime createdAt = LocalDateTime.now();
        Voucher validVoucher = new Voucher(voucherId, discount, createdAt);

        LocalDateTime usedAt = LocalDateTime.now();

        assertThatCode(() -> validVoucher.discountWith(50, usedAt))
                .doesNotThrowAnyException();
    }
}
