package org.prgrms.kdt.domain.voucher;

import org.apache.commons.math3.random.RandomDataGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.exception.ValidationException;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class FixedAmountVoucherTest {

    @Test
    @DisplayName("주어진 금액만큼 할인을 해야 한다.")
    void testDiscount() {
        var sut = new FixedAmountVoucher(new RandomDataGenerator().nextLong(0, 10000), VoucherType.FIXED_AMOUNT, 100L);
        assertEquals(900L, sut.discount(1000L));
    }

    @Test
    @DisplayName("할인 바우처 금액은 마이너스가 될 수 없다.")
    void testWithMinus() {
        assertThrows(ValidationException.class, () -> new FixedAmountVoucher(new RandomDataGenerator().nextLong(0, 10000), VoucherType.FIXED_AMOUNT, -100L));
    }

    @Test
    @DisplayName("할인된 금액은 마이너스가 될 수 없다.")
    void testMinusDiscountAmount() {
        var actual = new FixedAmountVoucher(new RandomDataGenerator().nextLong(0, 10000), VoucherType.FIXED_AMOUNT, 1000L);
        assertEquals(0, actual.discount(900L));
    }

    @Test
    @DisplayName("유효한 할인 금액으로만 생성할 수 있다.")
    void testVoucherCreation() {
        assertAll("FixedAmountVoucher creation",
                () -> assertThrows(ValidationException.class, () -> new FixedAmountVoucher(new RandomDataGenerator().nextLong(0, 10000), VoucherType.FIXED_AMOUNT, 0)),
                () -> assertThrows(ValidationException.class, () -> new FixedAmountVoucher(new RandomDataGenerator().nextLong(0, 10000), VoucherType.FIXED_AMOUNT, -100L)),
                () -> assertThrows(ValidationException.class, () -> new FixedAmountVoucher(new RandomDataGenerator().nextLong(0, 10000), VoucherType.FIXED_AMOUNT, 1000000L))
        );
    }

    @Test
    @DisplayName("FixedAmountVoucher의 할인된 금액을 확인한다.")
    void discount() {
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(new RandomDataGenerator().nextLong(0, 10000), VoucherType.FIXED_AMOUNT, 10L);

        long afterDiscount = fixedAmountVoucher.discount(200L);

        assertThat(afterDiscount).isEqualTo(190L);
    }
}
