package org.prgrms.vouchermanager.domain.voucher.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class PercentDiscountVoucherTest {

    @Test
    @DisplayName("퍼센트로 금액을 할인한다")
    void testWithPercentDiscount() {
        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 10);

        long discounted = percentDiscountVoucher.discount(100);

        assertThat(discounted).isEqualTo(90);
    }

    @Test
    @DisplayName("Amount는 0이 될 수 없다.")
    void testVoucherCreationWithZeroPercent() {
        assertThatIllegalArgumentException().isThrownBy(() -> new PercentDiscountVoucher(UUID.randomUUID(), 0));
    }

    @Test
    @DisplayName("Amount는 마이너스가 될 수 없다.")
    void testVoucherCreationWithMinusPercent() {
        assertThatIllegalArgumentException().isThrownBy(() -> new PercentDiscountVoucher(UUID.randomUUID(), -1));
    }

    @Test
    @DisplayName("Amount는 100을 초과할 수 없다.")
    void testVoucherCreationWithOverHundredPercent() {
        assertThatIllegalArgumentException().isThrownBy(() -> new PercentDiscountVoucher(UUID.randomUUID(), 101));
    }
}