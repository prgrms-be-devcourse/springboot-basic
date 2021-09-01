package org.prgrms.kdt.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.domain.voucher.FixedAmountVoucher;
import org.prgrms.kdt.exception.ValidationException;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class FixedAmountVoucherTest {

    @Test
    @DisplayName("주어진 금액만큼 할인을 해야 한다.")
    void testDiscount() {
        var sut = new FixedAmountVoucher(UUID.randomUUID(), 100L);
        assertEquals(900L, sut.discount(1000L));
    }

    @Test
    @DisplayName("할인 바우처 금액은 마이너스가 될 수 없다.")
    void testWithMinus() {
        assertThrows(ValidationException.class, () -> new FixedAmountVoucher(UUID.randomUUID(), -100L));
    }

    @Test
    @DisplayName("할인된 금액은 마이너스가 될 수 없다.")
    void testMinusDiscountAmount() {
        var actual = new FixedAmountVoucher(UUID.randomUUID(), 1000L);
        assertEquals(0, actual.discount(900L));
    }

    @Test
    @DisplayName("유효한 할인 금액으로만 생성할 수 있다.")
    void testVoucherCreation() {
        assertAll("FixedAmountVoucher creation",
                () -> assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(UUID.randomUUID(), 0)),
                () -> assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(UUID.randomUUID(), -100L)),
                () -> assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(UUID.randomUUID(), 1000000L))
        );
    }

    @Test
    @DisplayName("FixedAmountVoucher의 할인된 금액을 확인한다.")
    void discount() {
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 10L);

        long afterDiscount = fixedAmountVoucher.discount(200L);

        assertThat(afterDiscount).isEqualTo(190L);
    }
}
