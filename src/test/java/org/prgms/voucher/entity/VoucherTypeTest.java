package org.prgms.voucher.entity;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.prgms.voucher.exception.WrongInputVoucherCommandException;

class VoucherTypeTest {
    @DisplayName("command를 통해 해당하는 VoucherType을 반환한다.")
    @ParameterizedTest
    @CsvSource(value = {"1,FIXED_AMOUNT", "2,PERCENT_DISCOUNT"})
    void findByCommand_VoucherCommand_ReturnVoucherType(String command, VoucherType voucherType) throws
        WrongInputVoucherCommandException {
        assertThat(VoucherType.findByCommand(command)).isEqualTo(voucherType);
    }

    @DisplayName("올바르지 않은 command은 예외를 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"asd", "", "  ", "lists"})
    void findByCommand_WrongVoucherCommand_ThrowsException(String command) {
        assertThatThrownBy(() -> VoucherType.findByCommand(command))
            .isInstanceOf(WrongInputVoucherCommandException.class)
            .hasMessage("[ERROR] 올바른 바우처 커맨드가 아닙니다.");
    }
}
