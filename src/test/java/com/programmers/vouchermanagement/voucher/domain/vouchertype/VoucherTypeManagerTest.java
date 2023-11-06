package com.programmers.vouchermanagement.voucher.domain.vouchertype;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class VoucherTypeManagerTest {

    @Test
    @DisplayName("타입 이름을 FIXED로 입력한 경우 FixedAmountVoucherType 반환")
    void typeNameIsFIXED() {
        VoucherType voucherType = VoucherTypeManager.get("FIXED");

        assertThat(voucherType.getClass()).isEqualTo(FixedAmountVoucherType.class);
    }

    @Test
    @DisplayName("타입 이름을 PERCENT로 입력한 경우 PercentVoucherType 반환")
    void typeNameIsPERCENT() {
        VoucherType voucherType = VoucherTypeManager.get("PERCENT");

        assertThat(voucherType.getClass()).isEqualTo(PercentVoucherType.class);
    }

    @ParameterizedTest(name = "타입 입력이 {0}인 경우")
    @DisplayName("타입 이름을 잘못입력한 경우 예외 발생")
    @ValueSource(strings = {"ANOTHER", "FIIXED", "PERRCENT"})
    void typeNameIsNotCorrect(String input) {
        assertThrows(IllegalArgumentException.class, () -> {
            VoucherTypeManager.get(input);
        });
    }
}