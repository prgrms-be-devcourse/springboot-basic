package com.blessing333.springbasic.voucher.converter;

import com.blessing333.springbasic.voucher.domain.Voucher;
import com.blessing333.springbasic.voucher.dto.VoucherCreateForm;
import com.blessing333.springbasic.voucher.dto.VoucherCreateFormPayload;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class VoucherConverterTest {
    private final VoucherConverter voucherConverter = new VoucherConverter();

    @DisplayName("VoucherCreateForm 정상 변환")
    @Test
    void convertValidVoucherCreateForm() {
        String voucherTypeNumber = Voucher.VoucherType.FIXED.getOptionNumber();
        String discountAmount = "5000";
        VoucherCreateFormPayload voucherCreateFormPayload = new VoucherCreateFormPayload(voucherTypeNumber, discountAmount);

        VoucherCreateForm voucherCreateForm = voucherConverter.convert(voucherCreateFormPayload);

        assertThat(voucherCreateForm.getVoucherType()).isEqualTo(Voucher.VoucherType.FIXED);
        assertThat(voucherCreateForm.getDiscountAmount()).isEqualTo(Integer.parseInt(discountAmount));
    }

    @DisplayName("VoucherCreateForm 변환 실패 - 비정상적인 voucherType")
    @Test
    void convertWithInValidVoucherType() {
        String invalidVoucherTypeNumber = "-1";
        String discountAmount = "5000";
        VoucherCreateFormPayload voucherCreateFormPayload = new VoucherCreateFormPayload(invalidVoucherTypeNumber, discountAmount);

        assertThrows(ConvertFailException.class, () -> voucherConverter.convert(voucherCreateFormPayload));
    }

    @DisplayName("VoucherCreateForm 변환 실패 - 비정상적인 discountAmount")
    @Test
    void convertWithInValidDiscountAmount() {
        String voucherTypeNumber = Voucher.VoucherType.PERCENT.getOptionNumber();
        String discountAmount = "-1000";
        VoucherCreateFormPayload voucherCreateFormPayload = new VoucherCreateFormPayload(voucherTypeNumber, discountAmount);

        assertThrows(ConvertFailException.class, () -> voucherConverter.convert(voucherCreateFormPayload));
    }

}