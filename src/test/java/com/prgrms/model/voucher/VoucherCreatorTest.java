package com.prgrms.model.voucher;

import com.prgrms.model.voucher.discount.Discount;
import com.prgrms.model.voucher.discount.FixedDiscount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class VoucherCreatorTest {
    private static final Discount FIX_DISCOUNT = new FixedDiscount(20);
    private static final Discount PER_DISCOUNT = new FixedDiscount(20);

    @Test
    @DisplayName("고정된 금액의 바우처")
    public void createVoucher_FixedAmountVoucher_Equal() {
        //given
        VoucherType voucherType = VoucherType.FIXED_AMOUNT_VOUCHER;
        Discount discount = FIX_DISCOUNT;
        VoucherCreator voucherCreator = new VoucherCreator();
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(voucherCreator.getVoucherID(),discount,voucherType);

        //when
        Voucher result = voucherCreator.createVoucher(discount, voucherType);

        //then
        assertThat(result).isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(fixedAmountVoucher);

    }

    @Test
    @DisplayName("할인율에 따른 바우처")
    public void createVoucher_PercentDiscountVoucher_Equal() {
        //given
        VoucherType voucherType = VoucherType.PERCENT_DISCOUNT_VOUCHER;
        Discount discount = PER_DISCOUNT;
        VoucherCreator voucherCreator = new VoucherCreator();
        PercentDiscountVoucher percentDiscountVoucher= new PercentDiscountVoucher(voucherCreator.getVoucherID(),discount,voucherType);

        //when
        Voucher result = voucherCreator.createVoucher(discount, voucherType);

        //then
        assertThat(result).isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(percentDiscountVoucher);
    }
}
