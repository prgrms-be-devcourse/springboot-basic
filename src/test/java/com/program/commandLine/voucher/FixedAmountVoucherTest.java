package com.program.commandLine.voucher;

import com.program.commandLine.model.voucher.FixedAmountVoucher;
import com.program.commandLine.model.voucher.Voucher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FixedAmountVoucherTest {

    // Given
    UUID voucherId = UUID.randomUUID();
    Voucher fixedAmountVoucherSut = new FixedAmountVoucher(voucherId, 2000);


    @Test
    @DisplayName("Voucher의 주어진 금액만큼 할인이 된다.")
    void discountPrice() {
        var discountPrice = fixedAmountVoucherSut.discountPrice(8000);
        assertThat(discountPrice, is(6000));
    }

    @Test
    @DisplayName("Voucher의 할인된 금액은 마이너스가 될 수 없다.")
    void discountOverPrice() {
        var discountPrice = fixedAmountVoucherSut.discountPrice(1000);
        assertThat(discountPrice, is(0));
    }

    @Test
    @DisplayName("유효한 할인 금액만 생성된다.")
    void voucherCreation() {
        assertAll("FixedAmountVoucher creation",
                () -> assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(UUID.randomUUID(), 0)),
                () -> assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(UUID.randomUUID(), -100)),
                () -> assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(UUID.randomUUID(), 10000000)));
    }
}