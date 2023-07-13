package com.prgrms.model.voucher;

import com.prgrms.dto.voucher.VoucherRequest;
import com.prgrms.model.voucher.discount.Discount;
import com.prgrms.model.voucher.discount.FixedDiscount;
import com.prgrms.model.voucher.discount.PercentDiscount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class VoucherCreatorTest {

    private final int VOUCHER_ID = 1;
    private final Discount FIX_DISCOUNT = new FixedDiscount(20);
    private final Discount PERCENT_DISCOUNT = new PercentDiscount(20);

    @Autowired
    private VoucherCreator voucherCreator;


    @Test
    @DisplayName("고정 바우처 정첵 요청을 통해 바우처를 만들었을 때 요청에 맞는 올바른 바우처를 반환한다.")
    void createVoucher_FixedVoucher_Equal() {
        //given
        VoucherRequest voucherRequest = new VoucherRequest(VoucherType.FIXED_AMOUNT_VOUCHER, FIX_DISCOUNT);
        Voucher fixedVoucher = new FixedAmountVoucher(VOUCHER_ID, FIX_DISCOUNT, VoucherType.FIXED_AMOUNT_VOUCHER);

        // When
        Voucher result = voucherCreator.createVoucher(VOUCHER_ID, voucherRequest);

        // Then
        assertThat(result).usingRecursiveComparison().isEqualTo(fixedVoucher);
    }

    @Test
    @DisplayName("할인율 바우처 정첵 요청을 통해 바우처를 만들었을 때 요청에 맞는 올바른 바우처를 반환한다.")
    void createVoucher_PercentVoucher_Equal() {
        //given
        VoucherRequest voucherRequest = new VoucherRequest(VoucherType.PERCENT_DISCOUNT_VOUCHER, PERCENT_DISCOUNT);
        Voucher perecntVoucher = new PercentDiscountVoucher(VOUCHER_ID, PERCENT_DISCOUNT, VoucherType.PERCENT_DISCOUNT_VOUCHER);

        // When
        Voucher result = voucherCreator.createVoucher(VOUCHER_ID, voucherRequest);

        // Then
        assertThat(result).usingRecursiveComparison().isEqualTo(perecntVoucher);
    }


}
