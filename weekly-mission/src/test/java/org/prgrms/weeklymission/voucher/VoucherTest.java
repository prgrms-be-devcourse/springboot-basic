package org.prgrms.weeklymission.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.weeklymission.voucher.domain.FixedAmountVoucher;
import org.prgrms.weeklymission.voucher.domain.PercentDiscountVoucher;
import org.prgrms.weeklymission.voucher.domain.Voucher;

import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class VoucherTest {
    private Voucher voucher;

    @Test
    @DisplayName("FixedAmountVoucher 고정 할인 기능 1000 - 100 = 900")
    void test_fixed_discount() {
        voucher = new FixedAmountVoucher(randomUUID(), 100);
        long afterDiscount = voucher.discount(1000);

        assertThat(voucher.getVoucherId()).isNotNull();
        assertThat(afterDiscount).isEqualTo(900);
    }

    @Test
    @DisplayName("FixedAmountVoucher 할인된 금액이 0보다 작거나 원래 상품 가격보다 클 경우")
    void test_fixed_exception() {
        voucher = new FixedAmountVoucher(randomUUID(), 1000);
        //할인이 적용된 금액이(할인 금액(1000)) 0보다 작을 경우 예외가 발생해야 한다.
        assertThrows(RuntimeException.class, () -> voucher.discount(500));

        //할인이 적용된 금액이(할인 금액(-1)) 상품 가격보다 클 경우 에외가 발생해야 한다.
        voucher = new FixedAmountVoucher(randomUUID(), -1);
        assertThrows(RuntimeException.class, () -> voucher.discount(500));
    }

    @Test
    @DisplayName("PercentDiscountVoucher 퍼센트 할인 기능 1000 * (1000 * 10%) = 900")
    void test_percent_discount() {
        voucher = new PercentDiscountVoucher(randomUUID(), 10);
        long afterDiscount = voucher.discount(1000);

        assertThat(voucher.getVoucherId()).isNotNull();
        assertThat(afterDiscount).isEqualTo(900);
    }

    @Test
    @DisplayName("PercentDiscountVoucher 할인된 금액이 0보다 작거나 원래 상품 가격보다 클 경우")
    void test_percent_exception() {
        voucher = new PercentDiscountVoucher(randomUUID(), 101);
        //할인이 적용된 금액이(할인 비율(101)) 상품 가격보다 클 경우 예외가 발생해야 한다.
        assertThrows(RuntimeException.class, () -> voucher.discount(500));

        //할인이 적용된 금액이(할인 비율(-1)) 0보다 작을 경우 에외가 발생해야 한다.
        voucher = new FixedAmountVoucher(randomUUID(), -1);
        assertThrows(RuntimeException.class, () -> voucher.discount(500));
    }
}