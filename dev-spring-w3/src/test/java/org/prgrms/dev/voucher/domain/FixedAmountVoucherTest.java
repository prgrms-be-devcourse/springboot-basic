package org.prgrms.dev.voucher.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.dev.exception.InvalidArgumentException;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class FixedAmountVoucherTest {

    @DisplayName("주어진 금액만큼 할인을 해야한다.")
    @Test
    void discountTest() {
        Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100, LocalDateTime.now());
        assertEquals(900, fixedAmountVoucher.discount(1000));
    }

    @DisplayName("디스카운트된 금액은 마이너스가 될 수 없다.")
    @Test
    void minusDiscountedAmountTest() {
        Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 1000, LocalDateTime.now());
        assertEquals(0, fixedAmountVoucher.discount(900));
    }

    @DisplayName("유효한 할인 금액으로만 생성할 수 있다.")
    @Test
    void voucherCreationTest() {
        assertAll("FixedAmountVoucher creation",
                () -> assertThrows(InvalidArgumentException.class, () -> new FixedAmountVoucher(UUID.randomUUID(),
                        0, LocalDateTime.now()), "할인금액은 0원이 아니여야 합니다."),
                () -> assertThrows(InvalidArgumentException.class, () -> new FixedAmountVoucher(UUID.randomUUID(),
                        -100, LocalDateTime.now()), "할인금액은 양수여야 합니다."),
                () -> assertThrows(InvalidArgumentException.class, () -> new FixedAmountVoucher(UUID.randomUUID(),
                        100000, LocalDateTime.now()), "할인금액은 10000보다 작아야 합니다. ")
        );
    }
}