package com.zerozae.voucher.domain;

import com.zerozae.voucher.domain.voucher.FixedDiscountVoucher;
import com.zerozae.voucher.domain.voucher.UseStatusType;
import com.zerozae.voucher.domain.voucher.Voucher;
import com.zerozae.voucher.domain.voucher.VoucherType;
import com.zerozae.voucher.dto.voucher.VoucherUpdateRequest;
import com.zerozae.voucher.exception.ErrorMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VoucherTest {

    @Test
    @DisplayName("바우처 생성 성공 테스트")
    void createVoucher_Success_Test() {
        // Given
        long discount = 10L;

        // When
        Voucher voucher = new FixedDiscountVoucher(discount);

        // Then
        assertEquals(voucher.getDiscount(), discount);
    }

    @Test
    @DisplayName("바우처 생성 실패 테스트 (음수 값 입력)")
    void createVoucher_negativeInput_Failed_Test() {
        // Given
        long discount = -10L;

        // When & Then
        assertThrows(ErrorMessage.class, () -> {
            new FixedDiscountVoucher(discount);
        });
    }

    @Test
    @DisplayName("바우처 수정 성공 테스트")
    void updateVoucher_Success_Test() {
        // Given
        long discount = 10L;
        Voucher voucher = new FixedDiscountVoucher(discount);

        long newDiscount = 20L;
        UseStatusType newUseStatusType = UseStatusType.UNAVAILABLE;

        VoucherUpdateRequest voucherUpdateRequest = new VoucherUpdateRequest(newDiscount, newUseStatusType);

        // When
        voucher.updateVoucherInfo(voucherUpdateRequest);


        // Then
        assertEquals(voucher.getDiscount(), newDiscount);
        assertEquals(voucher.getUseStatusType(), newUseStatusType);
    }

    @Test
    @DisplayName("바우처 수정 실패 테스트 (음수 값 입력)")
    void updateVoucher_Failed_Test() {
        // Given
        long discount = 10L;
        Voucher voucher = new FixedDiscountVoucher(discount);

        long newDiscount = -20L;
        UseStatusType newUseStatusType = UseStatusType.UNAVAILABLE;

        VoucherUpdateRequest voucherUpdateRequest = new VoucherUpdateRequest(newDiscount, newUseStatusType);

        // When & Then
        assertThrows(ErrorMessage.class, () -> {
            voucher.updateVoucherInfo(voucherUpdateRequest);
        });
    }
}