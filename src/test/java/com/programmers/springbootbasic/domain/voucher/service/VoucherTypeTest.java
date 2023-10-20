package com.programmers.springbootbasic.domain.voucher.service;

import com.programmers.springbootbasic.domain.voucher.entity.FixedAmountVoucher;
import com.programmers.springbootbasic.domain.voucher.entity.PercentDiscountVoucher;
import com.programmers.springbootbasic.domain.voucher.entity.Voucher;
import com.programmers.springbootbasic.domain.voucher.exception.ErrorMsg;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("VoucherType Test")
class VoucherTypeTest {

    @DisplayName("of. FixedAmountVoucher 생성 성공")
    @Test
    void testOfMethodCreateFixedAmountVoucherSuccess() {
        // Arrange
        UUID expectedUUID = UUID.randomUUID();
        long expectedValue = 50L;
        // Act
        Voucher actualResult = VoucherType.of(1, expectedUUID, expectedValue);
        // Assert
        assertTrue(actualResult instanceof FixedAmountVoucher);
        assertEquals(expectedUUID, actualResult.getVoucherId());
    }

    @DisplayName("of. FixedAmountVoucher 생성 실패: value가 0 미만일때")
    @Test
    void testOfMethodCreateFixedAmountVoucherFailure() {
        // Arrange
        UUID expectedUUID = UUID.randomUUID();
        long expectedValue = -10L;
        // Act & Assert
        Throwable actualResult = assertThrows(IllegalArgumentException.class, () -> VoucherType.of(1, expectedUUID, expectedValue));
        assertEquals(ErrorMsg.wrongFixedAmountValueInput.getMessage(), actualResult.getMessage());
    }

    @DisplayName("of. PercentDiscountVoucher 생성 성공")
    @Test
    void testOfMethodCreatePercentDiscountVoucherSuccess() {
        // Arrange
        UUID expectedUUID = UUID.randomUUID();
        long expectedValue = 50L;
        // Act
        Voucher actualResult = VoucherType.of(2, expectedUUID, expectedValue);
        // Assert
        assertTrue(actualResult instanceof PercentDiscountVoucher);
        assertEquals(expectedUUID, actualResult.getVoucherId());
    }

    @DisplayName("of. PercentDiscountVoucher 생성 실패: value가 0 미만 or 100 초과일때")
    @Test
    void testOfMethodCreatePercentDiscountVoucherFailure() {
        // Arrange
        UUID expectedUUID = UUID.randomUUID();
        long expectedValue1 = -10L;
        long expectedValue2 = 101L;
        // Act & Assert
        Throwable actualResult = assertThrows(IllegalArgumentException.class, () -> VoucherType.of(2, expectedUUID, expectedValue1));
        assertEquals(ErrorMsg.wrongPercentDiscountValueInput.getMessage(), actualResult.getMessage());
        actualResult = assertThrows(IllegalArgumentException.class, () -> VoucherType.of(2, expectedUUID, expectedValue2));
        assertEquals(ErrorMsg.wrongPercentDiscountValueInput.getMessage(), actualResult.getMessage());
    }

    @DisplayName("of. number로 1,2가 아닌 숫자가 주어졌을때")
    @Test
    void testOfMethodWrongNumberGiven() {
        // Arrange
        UUID expectedUUID = UUID.randomUUID();
        long expectedValue = 10L;
        // Act & Assert
        Throwable actualResult = assertThrows(IllegalArgumentException.class, () -> VoucherType.of(-100, expectedUUID, expectedValue));
        assertEquals(ErrorMsg.wrongVoucherTypeNumber.getMessage(), actualResult.getMessage());
    }
}