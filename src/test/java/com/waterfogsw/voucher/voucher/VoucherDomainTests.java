package com.waterfogsw.voucher.voucher;

import com.waterfogsw.voucher.voucher.domain.Voucher;
import com.waterfogsw.voucher.voucher.domain.VoucherType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class VoucherDomainTests {
    @Nested
    @DisplayName("Voucher 의 생성자는")
    class Describe_voucher_constructor {
        @Nested
        @DisplayName("type 이 FIXED_AMOUNT 인 Voucher 의 value 가 음수이면")
        class Context_with_negative_fixedAmount {

            @Test
            @DisplayName("IllegalArgumentException 예외를 발생시킨다.")
            void it_throw_IllegalArgumentException() {
                assertThrows(IllegalArgumentException.class, () -> new Voucher(VoucherType.FIXED_AMOUNT, -1000));
            }
        }

        @Nested
        @DisplayName("type 이 PERCENT_DISCOUNT 인 Voucher 의 value 가 1 ~ 100 사이의 수가 아니면")
        class Context_with_out_of_range_percent_discount {

            @ParameterizedTest
            @ValueSource(ints = {-1, 101})
            @DisplayName("IllegalArgumentException 예외를 발생시킨다.")
            void it_throw_IllegalArgumentException(int percent) {

                assertThrows(IllegalArgumentException.class, () -> new Voucher(VoucherType.PERCENT_DISCOUNT, percent));
            }
        }
    }
}
