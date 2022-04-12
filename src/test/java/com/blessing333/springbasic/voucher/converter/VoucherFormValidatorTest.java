package com.blessing333.springbasic.voucher.converter;

import com.blessing333.springbasic.voucher.VoucherType;
import com.blessing333.springbasic.voucher.dto.VoucherCreateForm;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class VoucherFormValidatorTest {
    private final VoucherFormValidator validator = new VoucherFormValidator();

    @DisplayName("비정상적인 voucherType")
    @Test
    void validVoucherType() {
        String invalidVoucherTypeNumber = "-1";
        String discountAmount = "5000";
        VoucherCreateForm voucherCreateForm = new VoucherCreateForm(invalidVoucherTypeNumber, discountAmount);

        assertThrows(ConvertFailException.class, () -> validator.validateVoucherCreateForm(voucherCreateForm));
    }

    @DisplayName("PercentDiscountVoucher 검증 - 정상적인 discountAmount")
    @Test
    void validPercentVoucherWithCorrectDiscountAmount() {
        String voucherTypeNumber = VoucherType.PERCENT.getOptionNumber();
        String discountAmount = "50";
        VoucherCreateForm voucherCreateForm = new VoucherCreateForm(voucherTypeNumber, discountAmount);

        assertDoesNotThrow(() -> validator.validateVoucherCreateForm(voucherCreateForm));
    }

    @DisplayName("PercentDiscountVoucher 검증 - 비정상적인 discountAmount")
    @Test
    void validPercentVoucherWithIncorrectDiscountAmount() {
        String voucherTypeNumber = VoucherType.PERCENT.getOptionNumber();
        String discountAmount = "105";
        VoucherCreateForm voucherCreateForm = new VoucherCreateForm(voucherTypeNumber, discountAmount);

        assertThrows(ConvertFailException.class, () -> validator.validateVoucherCreateForm(voucherCreateForm));
    }

    @DisplayName("FixedAmountVoucher 검증 - 정상적인 discountAmount")
    @Test
    void validFixedVoucherWithCorrectDiscountAmount() {
        String voucherTypeNumber = VoucherType.FIXED.getOptionNumber();
        String discountAmount = "5000";
        VoucherCreateForm voucherCreateForm = new VoucherCreateForm(voucherTypeNumber, discountAmount);

        assertDoesNotThrow(() -> validator.validateVoucherCreateForm(voucherCreateForm));
    }

    @DisplayName("FixedAmountVoucher 검증 - 비정상적인 discountAmount")
    @Test
    void validFixedVoucherWithInCorrectDiscountAmount() {
        String voucherTypeNumber = VoucherType.FIXED.getOptionNumber();
        String discountAmount = "-100";
        VoucherCreateForm voucherCreateForm = new VoucherCreateForm(voucherTypeNumber, discountAmount);

        assertThrows(ConvertFailException.class, () -> validator.validateVoucherCreateForm(voucherCreateForm));
    }
}