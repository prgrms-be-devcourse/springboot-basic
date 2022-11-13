package org.prgrms.springorder.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.prgrms.springorder.domain.FixedAmountVoucher;
import org.prgrms.springorder.domain.PercentDiscountVoucher;
import org.prgrms.springorder.domain.Voucher;
import org.prgrms.springorder.domain.VoucherType;
import org.prgrms.springorder.request.VoucherCreateRequest;

@TestInstance(Lifecycle.PER_CLASS)
class VoucherFactoryTest {

    @DisplayName("voucherFactory 생성 테스트 - VoucherType이 FIXED면 FixedAmountVoucher가 생성된다.")
    @Test
    void createFixedVoucherSuccess() {
        //given
        VoucherType fixedVoucherType = VoucherType.FIXED;
        long discountAmount = 1000L;
        VoucherCreateRequest request = VoucherCreateRequest.of(fixedVoucherType, discountAmount);

        //when
        Voucher createdVoucher = VoucherFactory.create(request);
        //then
        assertEquals(FixedAmountVoucher.class, createdVoucher.getClass());
        assertEquals(fixedVoucherType, createdVoucher.getVoucherType());
    }

    @DisplayName("voucherFactory 생성 테스트 - VoucherType이 PERCENT면 PercentAmountVoucher가 생성된다.")
    @Test
    void createPercentVoucherSuccess() {
        //given
        VoucherType percentVoucherType = VoucherType.PERCENT;
        long discountAmount = 50L;
        VoucherCreateRequest request = VoucherCreateRequest.of(percentVoucherType, discountAmount);

        //when
        Voucher createdVoucher = VoucherFactory.create(request);
        //then
        assertEquals(PercentDiscountVoucher.class, createdVoucher.getClass());
        assertEquals(percentVoucherType, createdVoucher.getVoucherType());
    }


}