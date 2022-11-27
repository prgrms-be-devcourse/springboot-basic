package prgms.vouchermanagementapp.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import prgms.vouchermanagementapp.domain.value.Amount;
import prgms.vouchermanagementapp.service.VoucherCreationFactory;

class FixedAmountVoucherTest {

    @DisplayName("할인 전 금액이 고정 할인 금액보다 작은 경우 IllegalStateException 발생")
    @Test
    void should_throw_exception_when_amount_is_lower_than_fixedDiscountAmount() {
        // given
        long amountBeforeDiscount = 2000;
        Amount fixedDiscountAmount = new Amount(3000);

        // when
        Voucher voucher = VoucherCreationFactory.createVoucher(fixedDiscountAmount);

        // then
        Assertions.assertThrows(IllegalStateException.class, () -> {
            voucher.discount(amountBeforeDiscount);
        });
    }
}