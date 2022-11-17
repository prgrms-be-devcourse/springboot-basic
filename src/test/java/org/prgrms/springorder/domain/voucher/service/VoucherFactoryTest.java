package org.prgrms.springorder.domain.voucher.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.prgrms.springorder.domain.voucher.api.request.VoucherCreateRequest;
import org.prgrms.springorder.domain.voucher.model.FixedAmountVoucher;
import org.prgrms.springorder.domain.voucher.model.PercentDiscountVoucher;
import org.prgrms.springorder.domain.voucher.model.Voucher;
import org.prgrms.springorder.domain.voucher.model.VoucherType;

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

    @DisplayName("voucherFactory 생성 테스트 - VoucherType이 FIXED면 FixedAmountVoucher가 생성된다.")
    @Test
    void toFixedVoucherTest() {
        //given
        VoucherType fixedVoucherType = VoucherType.FIXED;
        long discountAmount = 1000L;
        UUID voucherId = UUID.randomUUID();
        UUID customerId = UUID.randomUUID();
        LocalDateTime createdAt = LocalDateTime.now();

        //when
        Voucher fixedAmountVoucher = VoucherFactory.toVoucher(fixedVoucherType, voucherId, discountAmount,
            customerId, createdAt);

        //then
        assertEquals(FixedAmountVoucher.class, fixedAmountVoucher.getClass());
        assertEquals(fixedVoucherType, fixedAmountVoucher.getVoucherType());
        assertEquals(discountAmount, fixedAmountVoucher.getAmount());
        assertEquals(customerId, fixedAmountVoucher.getCustomerId());
        assertEquals(createdAt, fixedAmountVoucher.getCreatedAt());
    }


    @DisplayName("voucherFactory 생성 테스트 - VoucherType이 PERCENT면 PercentAmountVoucher가 생성된다.")
    @Test
    void toPercentVoucherTest() {
        //given
        VoucherType percentVoucherType = VoucherType.PERCENT;
        long discountAmount = 50L;
        UUID voucherId = UUID.randomUUID();
        UUID customerId = UUID.randomUUID();
        LocalDateTime createdAt = LocalDateTime.now();

        //when
        Voucher percentDiscountVoucher = VoucherFactory.toVoucher(percentVoucherType, voucherId, discountAmount,
            customerId, createdAt);

        //then
        assertEquals(PercentDiscountVoucher.class, percentDiscountVoucher.getClass());
        assertEquals(percentVoucherType, percentDiscountVoucher.getVoucherType());
        assertEquals(discountAmount, percentDiscountVoucher.getAmount());
        assertEquals(customerId, percentDiscountVoucher.getCustomerId());
        assertEquals(createdAt, percentDiscountVoucher.getCreatedAt());
    }

}