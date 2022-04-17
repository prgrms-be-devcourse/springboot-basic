package org.prgms.voucherProgram.domain.voucher;

import static org.assertj.core.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class VoucherTypeTest {
    @DisplayName("command를 통해 해당하는 VoucherType을 반환한다.")
    @ParameterizedTest
    @CsvSource(value = {"1,FIXED_AMOUNT", "2,PERCENT_DISCOUNT"})
    void findByCommand_VoucherCommand_ReturnVoucherType(int command, VoucherType voucherType) {
        assertThat(VoucherType.findByNumber(command)).isEqualTo(voucherType);
    }

    @DisplayName("올바르지 않은 command은 예외를 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"6", "5", "4", "3"})
    void findByCommand_WrongVoucherCommand_ThrowsException(int command) {
        assertThatThrownBy(() -> VoucherType.findByNumber(command))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("[ERROR] 올바른 바우처 타입이 아닙니다.");
    }

    @DisplayName("FIXED_AMOUNT일때 FixedAmountVoucher를 생성한다.")
    @Test
    void should_CreateFixedAmountVoucher_When_FIXED_AMOUNT() {
        assertThat(VoucherType.FIXED_AMOUNT.createVoucher(UUID.randomUUID(), UUID.randomUUID(), 10L)).isInstanceOf(
            FixedAmountVoucher.class);
    }

    @DisplayName("PERCENT_DISCOUNT일때 PercentDiscountVoucher를 생성한다.")
    @Test
    void should_CreatePercentDiscountVoucher_When_PERCENT_DISCOUNT() {
        assertThat(VoucherType.PERCENT_DISCOUNT.createVoucher(UUID.randomUUID(), UUID.randomUUID(), 10L)).isInstanceOf(
            PercentDiscountVoucher.class);
    }
}
