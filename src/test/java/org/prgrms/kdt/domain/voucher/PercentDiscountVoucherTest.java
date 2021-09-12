package org.prgrms.kdt.domain.voucher;

import org.apache.commons.math3.random.RandomDataGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.error.ValidationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PercentDiscountVoucherTest {

    @Test
    @DisplayName("주어진 금액만큼 할인을 해야 한다.")
    void testDiscount() {
        var sut = new PercentDiscountVoucher(new RandomDataGenerator().nextLong(0, 10000), VoucherType.PERCENT_DISCOUNT, 10L);
        assertEquals(900L, sut.discount(1000L));
    }

    @Test
    @DisplayName("할인 바우처 금액은 마이너스가 될 수 없다.")
    void testWithMinus() {
        assertThrows(ValidationException.class, () -> new PercentDiscountVoucher(new RandomDataGenerator().nextLong(0, 10000), VoucherType.PERCENT_DISCOUNT, -100L));
    }

    @Test
    @DisplayName("유효한 할인 금액으로만 생성할 수 있다.")
    void testVoucherCreation() {
        assertAll("PercentDiscountVoucher creation",
                () -> assertThrows(ValidationException.class, () -> new FixedAmountVoucher(new RandomDataGenerator().nextLong(0, 10000), VoucherType.PERCENT_DISCOUNT, 0)),
                () -> assertThrows(ValidationException.class, () -> new FixedAmountVoucher(new RandomDataGenerator().nextLong(0, 10000), VoucherType.PERCENT_DISCOUNT, -100L)),
                () -> assertThrows(ValidationException.class, () -> new FixedAmountVoucher(new RandomDataGenerator().nextLong(0, 10000), VoucherType.PERCENT_DISCOUNT, 1000000L))
        );
    }


    @Test
    @DisplayName("PercentDiscountVoucher의 할인된 금액을 확인한다.")
    void discount() {
        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(new RandomDataGenerator().nextLong(0, 10000), VoucherType.PERCENT_DISCOUNT, 10L);

        long afterDiscount = percentDiscountVoucher.discount(200L);

        assertThat(afterDiscount).isEqualTo(180L);
    }
}
