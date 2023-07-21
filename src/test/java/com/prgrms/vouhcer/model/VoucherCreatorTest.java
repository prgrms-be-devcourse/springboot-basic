package com.prgrms.vouhcer.model;

import static org.assertj.core.api.Assertions.assertThat;

import com.prgrms.voucher.model.FixedAmountVoucher;
import com.prgrms.voucher.model.PercentDiscountVoucher;
import com.prgrms.voucher.model.Voucher;
import com.prgrms.voucher.model.VoucherCreator;
import com.prgrms.voucher.model.VoucherType;
import com.prgrms.voucher.model.discount.Discount;
import com.prgrms.voucher.model.discount.FixedDiscount;
import com.prgrms.voucher.model.discount.PercentDiscount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class VoucherCreatorTest {

    private final int voucherId = 1;
    private final double discountAmount = 20;
    private final Discount fixedDiscount = new FixedDiscount(discountAmount);
    private final Discount percentDiscount = new PercentDiscount(discountAmount);

    @Autowired
    private VoucherCreator voucherCreator;


    @Test
    @DisplayName("고정 금액 바우처 요청을 통해 바우처를 만들었을 때 요청과 같은 바우처를 반환한다.")
    void createVoucher_FixedVoucher_Equal() {
        //given
        Voucher fixedVoucher = new FixedAmountVoucher(voucherId, fixedDiscount,
                VoucherType.FIXED_AMOUNT_VOUCHER);

        // When
        Voucher result = voucherCreator.createVoucher(voucherId, VoucherType.FIXED_AMOUNT_VOUCHER,
                fixedDiscount);

        // Then
        assertThat(result).usingRecursiveComparison().isEqualTo(fixedVoucher);
    }

    @Test
    @DisplayName("할인율 바우처 요청을 통해 바우처를 만들었을 때 요청과 같은 바우처를 반환한다.")
    void createVoucher_PercentVoucher_Equal() {
        //given
        Voucher perecntVoucher = new PercentDiscountVoucher(voucherId, percentDiscount,
                VoucherType.PERCENT_DISCOUNT_VOUCHER);

        // When
        Voucher result = voucherCreator.createVoucher(voucherId,
                VoucherType.PERCENT_DISCOUNT_VOUCHER,
                percentDiscount);

        // Then
        assertThat(result).usingRecursiveComparison().isEqualTo(perecntVoucher);
    }

}
