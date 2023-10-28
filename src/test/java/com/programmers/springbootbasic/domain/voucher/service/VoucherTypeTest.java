package com.programmers.springbootbasic.domain.voucher.service;

import com.programmers.springbootbasic.domain.voucher.entity.FixedAmountVoucher;
import com.programmers.springbootbasic.domain.voucher.entity.PercentDiscountVoucher;
import com.programmers.springbootbasic.domain.voucher.entity.Voucher;
import com.programmers.springbootbasic.domain.voucher.exception.ErrorMsg;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        assertThat(actualResult).isInstanceOf(FixedAmountVoucher.class);
        assertThat(actualResult.getVoucherId()).isEqualTo(expectedUUID);
        assertThat(actualResult.getValue()).isEqualTo(expectedValue);
    }

    @DisplayName("of. FixedAmountVoucher 생성 실패: value가 0 미만일때")
    @Test
    void testOfMethodCreateFixedAmountVoucherFailure() {
        // Arrange
        UUID expectedUUID = UUID.randomUUID();
        long expectedValue = -10L;
        // Act & Assert
        Throwable actualResult = assertThrows(IllegalArgumentException.class, () -> VoucherType.of(1, expectedUUID, expectedValue));
        assertThat(actualResult.getMessage()).isEqualTo(ErrorMsg.WRONG_FIXED_AMOUNT_VALUE_INPUT.getMessage());
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
        assertThat(actualResult).isInstanceOf(PercentDiscountVoucher.class);
        assertThat(actualResult.getVoucherId()).isEqualTo(expectedUUID);
        assertThat(actualResult.getValue()).isEqualTo(expectedValue);
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
        assertThat(actualResult.getMessage()).isEqualTo(ErrorMsg.WRONG_PERCENT_DISCOUNT_VALUE_INPUT.getMessage());
        actualResult = assertThrows(IllegalArgumentException.class, () -> VoucherType.of(2, expectedUUID, expectedValue2));
        assertThat(actualResult.getMessage()).isEqualTo(ErrorMsg.WRONG_PERCENT_DISCOUNT_VALUE_INPUT.getMessage());
    }

    @DisplayName("of. number로 1,2가 아닌 숫자가 주어졌을때")
    @Test
    void testOfMethodWrongNumberGiven() {
        // Arrange
        UUID expectedUUID = UUID.randomUUID();
        long expectedValue = 10L;
        // Act & Assert
        Throwable actualResult = assertThrows(IllegalArgumentException.class, () -> VoucherType.of(-100, expectedUUID, expectedValue));
        assertThat(actualResult.getMessage()).isEqualTo(ErrorMsg.WRONG_VOUCHER_TYPE_NUMBER.getMessage());
    }
}