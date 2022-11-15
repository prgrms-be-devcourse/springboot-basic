package org.prgrms.springorder.domain.voucher.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.prgrms.springorder.domain.voucher.model.VoucherType;

class VoucherTypeTest {

    @DisplayName("생성 성공 테스트 - 인자가 percent, fixed이면 객체 생성에 성공한다")
    @ParameterizedTest
    @CsvSource(value = {"percent", "fixed"})
    void createSuccessPercentAndFixed(String voucherType) {
        //given & when & then
        assertDoesNotThrow(() -> VoucherType.of(voucherType));
    }

    @DisplayName("생성 실패 테스트 - 인자가 percent, fixed이 아니면 객체 생성에 실패한다")
    @ParameterizedTest
    @CsvSource(value = {"percentt", "fixedd", "fercent", "pixed"})
    void createFailNotPercentAndFixed(String voucherType) {
        //given & when & then
        assertThrows(IllegalArgumentException.class, () -> VoucherType.of(voucherType));
    }

}