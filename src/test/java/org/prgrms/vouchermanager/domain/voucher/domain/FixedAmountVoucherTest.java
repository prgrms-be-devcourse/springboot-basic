package org.prgrms.vouchermanager.domain.voucher.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class FixedAmountVoucherTest {

    @Test
    @DisplayName("고정 금액을 디스카운트한다.")
    void testWIthDiscountAmount() {
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 10L);
        long beforeDiscountAmount = 100;

        long discounted = fixedAmountVoucher.discount(beforeDiscountAmount);

        assertThat(discounted).isEqualTo(90);
    }

    @Test
    @DisplayName("할인된 금액은 마이너스가 될 수 없다.")
    void testMinusDiscountedAmount() {
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 1000L);
        long discounted = fixedAmountVoucher.discount(100);

        assertThat(discounted).isEqualTo(0);
    }

    @Test
    @DisplayName("할인액은 0이 될 수 없다.")
    void testVoucherCreationWithZeroAmount() {
        assertThatIllegalArgumentException().isThrownBy(() -> new FixedAmountVoucher(UUID.randomUUID(), 0));
    }

    @Test
    @DisplayName("할인액은 minus가 될 수 없다.")
    void testVoucherCreationWithMinusAmount() {
        assertThatIllegalArgumentException().isThrownBy(() -> new FixedAmountVoucher(UUID.randomUUID(), -10));
    }

    @Test
    @DisplayName("할인액은 MAX_VOUCHER_AMOUNT을 초과할 수 없다.")
    void testVoucherCreationWithOverMaxAmount() {
        assertThatIllegalArgumentException().isThrownBy(() -> new FixedAmountVoucher(UUID.randomUUID(), FixedAmountVoucher.MAX_FIXED_VOUCHER_AMOUNT + 1));
    }
}