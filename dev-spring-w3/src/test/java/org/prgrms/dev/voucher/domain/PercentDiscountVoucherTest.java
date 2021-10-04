package org.prgrms.dev.voucher.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.dev.exception.InvalidArgumentException;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PercentDiscountVoucherTest {
    @DisplayName("주어진 할인율만큼 할인을 해야한다.")
    @Test
    void discountTest() {
        Voucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 30, LocalDateTime.now());
        assertEquals(2100, percentDiscountVoucher.discount(3000));
    }

    @DisplayName("유효한 할인율(0%~100%)으로만 생성할 수 있다.")
    @Test
    void voucherCreationTest() {
        assertAll("PercentDiscountVoucher creation",
                () -> assertThrows(InvalidArgumentException.class, () -> new PercentDiscountVoucher(UUID.randomUUID(),
                        -10, LocalDateTime.now()), "할일율은 0이 아니여야 합니다."),
                () -> assertThrows(InvalidArgumentException.class, () -> new PercentDiscountVoucher(UUID.randomUUID(),
                        0, LocalDateTime.now()), "할인율은 양수여야 합니다."),
                () -> assertThrows(InvalidArgumentException.class, () -> new PercentDiscountVoucher(UUID.randomUUID(),
                        1000, LocalDateTime.now()), "할인율은 100보다 작아야 합니다.")
        );
    }

}