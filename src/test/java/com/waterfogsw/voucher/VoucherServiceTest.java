package com.waterfogsw.voucher;

import com.waterfogsw.voucher.exception.InvalidVoucherException;
import com.waterfogsw.voucher.voucher.VoucherService;
import com.waterfogsw.voucher.voucher.VoucherServiceImpl;
import com.waterfogsw.voucher.voucher.VoucherType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

class VoucherServiceTest {
    VoucherService voucherService = new VoucherServiceImpl();

    //바우처 서비스
    @Test
    @DisplayName("바우처 생성 - FixedAmount")
    public void createFixedAmountVoucher() {
        //given
        VoucherType type = VoucherType.FIXED_AMOUNT;
        Double amount = 10d;

        //when
        var voucher = voucherService.createVoucher(type, amount);

        //then
        Assertions.assertThat(voucher.getType()).isEqualTo(VoucherType.FIXED_AMOUNT);
    }

    @Test
    @DisplayName("바우처 생성 - PercentDiscount")
    public void createPercentAmountVoucher() {
        //given
        VoucherType type = VoucherType.PERCENT_DISCOUNT;
        Double percent = 10d;

        //when
        var voucher = voucherService.createVoucher(type, percent);

        //then
        Assertions.assertThat(voucher.getType()).isEqualTo(VoucherType.PERCENT_DISCOUNT);
    }

    @Test
    @DisplayName("FixedAmountVoucher - 음수인 경우")
    public void testAmountMinus() {
        //given
        VoucherType type = VoucherType.FIXED_AMOUNT;
        Double amount = 10d;

        //when, then
        try {
            voucherService.createVoucher(type, amount);
            fail();
        } catch (InvalidVoucherException e){
            //pass
        }

    }

    @Test
    @DisplayName("PercentAmountVoucher - 음수인 경우")
    public void testPercentMinus() {
        //given
        VoucherType type = VoucherType.PERCENT_DISCOUNT;
        Double percent = 10d;

        //when, then
        try {
            voucherService.createVoucher(type, percent);
            fail();
        } catch (InvalidVoucherException e){
            //pass
        }
    }

    @Test
    @DisplayName("PercentAmountVoucher - 100을 넘는 경우")
    public void testPercentHundred() {
        //given
        VoucherType type = VoucherType.PERCENT_DISCOUNT;
        Double percent = 120d;

        //when, then
        try {
            voucherService.createVoucher(type, percent);
            fail();
        } catch (InvalidVoucherException e){
            //pass
        }
    }
}
