package com.waterfogsw.voucher.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class VoucherServiceTests {

    @Nested
    @DisplayName("addVoucher 메소드는")
    class Describe_addVoucher {

        @Nested
        @DisplayName("type 이 FIXED_AMOUNT 인 Voucher 의 value 가 음수이면")
        class Context_with_negative_fixedAmount {

            @Test
            @DisplayName("IllegalArgumentException 예외를 발생시킨다.")
            void it_throw_IllegalArgumentException() {

            }
        }

        @Nested
        @DisplayName("type 이 PERCENT_DISCOUNT 인 Voucher 의 value 가 1 ~ 100 사이의 수가 아니면")
        class Context_with_out_of_range_percent_discount {

            @Test
            @DisplayName("IllegalArgumentException 예외를 발생시킨다.")
            void it_throw_IllegalArgumentException() {

            }
        }

        @Nested
        @DisplayName("Voucher 가 정상적으로 저장되면")
        class Context_with_saved_success {

            @Test
            @DisplayName("저장한 Voucher 를 return 한다")
            void it_throw_IllegalArgumentException() {

            }
        }
    }
}
