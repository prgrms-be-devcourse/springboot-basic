package com.zerozae.voucher.domain;

import com.zerozae.voucher.domain.voucher.FixedDiscountVoucher;
import com.zerozae.voucher.domain.voucher.Voucher;
import com.zerozae.voucher.exception.ErrorMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VoucherTest {

    @Test
    @DisplayName("바우처 생성 성공 테스트")
    void createVoucherSuccessTest(){
        // Given
        long discount = 10L;

        // When
        Voucher voucher = new FixedDiscountVoucher(discount);

        // Then
        assertEquals(voucher.getDiscount(), discount);
    }

    @Test
    @DisplayName("바우처 생성 실패 테스트 (음수 값 입력)")
    void createVoucherFailedTest(){
        // Given
        long discount = -10L;

        // When

        // Then
        assertThrows(ErrorMessage.class, () -> {
            new FixedDiscountVoucher(discount);
        });
    }
}
