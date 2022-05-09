package com.prgrms.vouchermanagement.voucher;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.prgrms.vouchermanagement.voucher.VoucherType.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class VoucherTypeTest {

    @Test
    @DisplayName("voucher 번호를 입력하면 일치하는 VoucherType이 반환된다")
    void getVoucherTypeByOrderTest() {
        // given
        int fixedVoucherOrder = 1;
        int percentVoucherOrder = 2;

        // when
        VoucherType fixedVoucherType = getVoucherType(fixedVoucherOrder);
        VoucherType percentVoucherType = getVoucherType(percentVoucherOrder);

        // then
        assertThat(fixedVoucherType).isEqualTo(FIXED_DISCOUNT);
        assertThat(percentVoucherType).isEqualTo(PERCENT_DISCOUNT);
    }

    @ParameterizedTest
    @ValueSource(ints = {-10, -1, 5, 10, 100, 10000})
    @DisplayName("잘못된 voucher 번호를 입력하면 IllegalArgumentException이 발생한다. ")
    void getVoucherTypeByWrongOrderTest(int wrongVoucherOrder) {
        assertThrows(IllegalArgumentException.class, () -> {
            getVoucherType(wrongVoucherOrder);
        });
    }

    @Test
    @DisplayName("voucher가 어떤 VoucherType인지 반환해준다.")
    void getVoucherTypeByVoucherTest() {
        // given
        Voucher fixedVoucher = FIXED_DISCOUNT.constructor(500, LocalDateTime.now());
        Voucher percentVoucher = PERCENT_DISCOUNT.constructor(50, LocalDateTime.now());

        // when
        VoucherType fixedVoucherType = getVoucherType(fixedVoucher);
        VoucherType percentVoucherType = getVoucherType(percentVoucher);

        // then
        assertThat(fixedVoucherType).isEqualTo(FIXED_DISCOUNT);
        assertThat(percentVoucherType).isEqualTo(PERCENT_DISCOUNT);
    }

    @Test
    @DisplayName("FixedAmountVoucher를 생성한다.")
    void createFixedAmountVoucherTest() {
        // given
        int amount = 500;
        LocalDateTime createdAt = LocalDateTime.now();

        // when
        Voucher voucher = FIXED_DISCOUNT.constructor(amount, createdAt);

        // then
        assertThat(voucher.getAmount()).isEqualTo(amount);
        assertThat(voucher.getCreatedAt()).isEqualTo(createdAt);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -5, -10, -100, -1000})
    @DisplayName("0보다 작은 할인액으로 FixedAmountVoucher를 생성하면 IllegalArgumentException이 발생한다.")
    void createFixedAmountVoucherFailTest(int wrongAmount) {

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Voucher voucher = FIXED_DISCOUNT.constructor(wrongAmount, LocalDateTime.now());
        });
    }

    @Test
    @DisplayName("PercentDiscountVoucher를 생성한다.")
    void createPercentDiscountVoucherTest() {
        // given
        UUID voucherId = UUID.randomUUID();
        int amount = 50;
        LocalDateTime createdAt = LocalDateTime.now();

        // when
        Voucher voucher = PERCENT_DISCOUNT.constructor(amount, createdAt);

        // then
        assertThat(voucher.getAmount()).isEqualTo(amount);
        assertThat(voucher.getCreatedAt()).isEqualTo(createdAt);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -5, -10, 101, 1000, 10000})
    @DisplayName("0~100을 벗어나는 작은 할인율로 PercentVoucher를 생성하면 IllegalArgumentException이 발생한다.")
    void createPercentDiscountVoucherFailTest(int wrongAmount) {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Voucher voucher = PERCENT_DISCOUNT.constructor(wrongAmount, LocalDateTime.now());
        });
    }
}