package com.blessing333.springbasic.domain.voucher.converter;

import com.blessing333.springbasic.domain.voucher.dto.VoucherCreateForm;
import com.blessing333.springbasic.domain.voucher.dto.VoucherCreateFormPayload;
import com.blessing333.springbasic.domain.voucher.model.Voucher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class VoucherPayloadConverterTest {
    private final VoucherPayloadConverter voucherPayloadConverter = new VoucherPayloadConverter();

    @DisplayName("VoucherCreateForm 정상 변환")
    @Test
    void convertValidVoucherCreateForm() {
        String voucherTypeNumber = Voucher.VoucherType.FIXED.getOptionNumber();
        String discountAmount = "5000";
        VoucherCreateFormPayload voucherCreateFormPayload = new VoucherCreateFormPayload(voucherTypeNumber, discountAmount);

        VoucherCreateForm voucherCreateForm = voucherPayloadConverter.toCreateForm(voucherCreateFormPayload);

        assertThat(voucherCreateForm.getVoucherType()).isEqualTo(Voucher.VoucherType.FIXED);
        assertThat(voucherCreateForm.getDiscountAmount()).isEqualTo(Integer.parseInt(discountAmount));
    }

    @DisplayName("VoucherCreateForm 변환 실패 - 비정상적인 voucherType")
    @Test
    void convertWithInValidVoucherType() {
        String invalidVoucherTypeNumber = "-1";
        String discountAmount = "5000";
        VoucherCreateFormPayload voucherCreateFormPayload = new VoucherCreateFormPayload(invalidVoucherTypeNumber, discountAmount);

        assertThrows(ConvertFailException.class, () -> voucherPayloadConverter.toCreateForm(voucherCreateFormPayload));
    }

    @DisplayName("VoucherCreateForm 변환 실패 - 비정상적인 discountAmount")
    @Test
    void convertWithInValidDiscountAmount() {
        String voucherTypeNumber = Voucher.VoucherType.PERCENT.getOptionNumber();
        String discountAmount = "-1000";
        VoucherCreateFormPayload voucherCreateFormPayload = new VoucherCreateFormPayload(voucherTypeNumber, discountAmount);

        assertThrows(ConvertFailException.class, () -> voucherPayloadConverter.toCreateForm(voucherCreateFormPayload));
    }

}