package com.waterfogsw.voucher.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class VoucherMemoryRepositoryTests {

    @Nested
    @DisplayName("save 메소드는")
    class Describe_save {
        @Nested
        @DisplayName("인자로 전달받은 Voucher 가 null 이면")
        class Context_with_null_argument {

            @Test
            @DisplayName("IllegalArgumentException 예외를 발생시킨다")
            void it_throw_error() {

            }
        }

        @Nested
        @DisplayName("Voucher 가 정상적으로 저장되면")
        class Context_with_saved_success {

            @Test
            @DisplayName("저장한 Voucher 를 return 한다")
            void it_return_saved_voucher() {

            }
        }
    }
}
