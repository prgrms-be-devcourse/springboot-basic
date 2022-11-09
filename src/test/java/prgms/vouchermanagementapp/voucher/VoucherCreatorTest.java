package prgms.vouchermanagementapp.voucher;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import prgms.vouchermanagementapp.model.Amount;
import prgms.vouchermanagementapp.voucher.model.FixedAmountVoucher;

class VoucherCreatorTest {

    VoucherCreator voucherCreator = new VoucherCreator();

    @Test
    @DisplayName("FixedAmountVoucher 는 고정된 금액을 할인한다.")
    void FixedAmountVoucher_creation_test() {
        // given
        long amount = 3000L;
        Amount fixedDiscountAmount = new Amount(amount);
        FixedAmountVoucher fixedAmountVoucher = voucherCreator.createFixedAmountVoucher(fixedDiscountAmount);

        // when
        long amountBeforeDiscount = 10000L;
        long discountedAmount = fixedAmountVoucher.discount(amountBeforeDiscount);

        // then
        Assertions.assertThat(discountedAmount).isEqualTo(7000L);
    }
}