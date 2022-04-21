package com.blessing333.springbasic.voucher.converter;

import com.blessing333.springbasic.voucher.VoucherType;
import com.blessing333.springbasic.voucher.dto.ConvertedVoucherCreateForm;
import com.blessing333.springbasic.voucher.dto.VoucherCreateForm;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ConverterTest {
    private final Converter converter = new Converter();

    @DisplayName("VoucherCreateForm 정상 변환")
    @Test
    void convertValidVoucherCreateForm() {
        String voucherTypeNumber = VoucherType.FIXED.getOptionNumber();
        String discountAmount = "5000";
        VoucherCreateForm voucherCreateForm = new VoucherCreateForm(voucherTypeNumber, discountAmount);

        ConvertedVoucherCreateForm convertedVoucherCreateForm = converter.convert(voucherCreateForm);

        assertThat(convertedVoucherCreateForm.getVoucherType()).isEqualTo(VoucherType.FIXED);
        assertThat(convertedVoucherCreateForm.getDiscountAmount()).isEqualTo(Integer.parseInt(discountAmount));
    }

    @DisplayName("VoucherCreateForm 변환 실패 - 비정상적인 voucherType")
    @Test
    void convertWithInValidVoucherType() {
        String invalidVoucherTypeNumber = "-1";
        String discountAmount = "5000";
        VoucherCreateForm voucherCreateForm = new VoucherCreateForm(invalidVoucherTypeNumber, discountAmount);

        assertThrows(ConvertFailException.class, () -> converter.convert(voucherCreateForm));
    }

    @DisplayName("VoucherCreateForm 변환 실패 - 비정상적인 discountAmount")
    @Test
    void convertWithInValidDiscountAmount() {
        String voucherTypeNumber = VoucherType.PERCENT.getOptionNumber();
        String discountAmount = "-1000";
        VoucherCreateForm voucherCreateForm = new VoucherCreateForm(voucherTypeNumber, discountAmount);

        assertThrows(ConvertFailException.class, () -> converter.convert(voucherCreateForm));
    }

}