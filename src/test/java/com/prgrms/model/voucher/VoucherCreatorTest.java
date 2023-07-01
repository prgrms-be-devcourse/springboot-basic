package com.prgrms.model.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VoucherCreatorTest {
    private static final Discount DISCOUNT = new Discount(20);

    @Test
    @DisplayName("고정된 금액의 바우처")
    public void testCreateVoucherFixedAmountVoucher() {
        VoucherPolicy voucherPolicy = VoucherPolicy.FixedAmountVoucher;
        VoucherCreator voucherCreator = new VoucherCreator();

        Voucher result = voucherCreator.createVoucher(DISCOUNT, voucherPolicy);

        assertNotNull(result);
        assertTrue(result instanceof FixedAmountVoucher);
        assertEquals(DISCOUNT, result.getVoucherDiscount());
        assertEquals(voucherPolicy, result.getVoucherPolicy());
    }

    @Test
    @DisplayName("할인율에 따른 바우처")
    public void testCreateVoucher_PercentDiscountVoucher_ReturnsPercentDiscountVoucher() {
        VoucherPolicy voucherPolicy = VoucherPolicy.PercentDiscountVoucher;
        VoucherCreator voucherCreator = new VoucherCreator();

        Voucher result = voucherCreator.createVoucher(DISCOUNT, voucherPolicy);

        assertNotNull(result);
        assertTrue(result instanceof PercentDiscountVoucher);
        assertEquals(DISCOUNT, result.getVoucherDiscount());
        assertEquals(voucherPolicy, result.getVoucherPolicy());
    }

    @Test
    @DisplayName("잘못된 바우처 정책에 대해서 예외를 던지는지 테스트")
    public void testCreateVoucher_InvalidVoucherPolicy_ThrowsException() {
        String invalidVoucherPolicy = "라면";
        VoucherCreator voucherCreator = new VoucherCreator();

        assertThrows(IllegalArgumentException.class, () ->
                voucherCreator.createVoucher(DISCOUNT, VoucherPolicy.valueOf(invalidVoucherPolicy)));
    }
}