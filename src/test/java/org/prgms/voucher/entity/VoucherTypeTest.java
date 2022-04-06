package org.prgms.voucher.entity;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class VoucherTypeTest {
    @DisplayName("해당하는 VoucherType을 반환한다.")
    @ParameterizedTest
    @CsvSource(value = {"1,FIXED_AMOUNT", "2,PERCENT_DISCOUNT", "asd,EMPTY", ",EMPTY"})
    void of_VoucherCommand_ReturnVoucherType(String command, VoucherType voucherType) {
        assertThat(VoucherType.of(command)).isEqualTo(voucherType);
    }
}
