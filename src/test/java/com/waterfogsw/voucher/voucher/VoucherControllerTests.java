package com.waterfogsw.voucher.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class VoucherControllerTests {
    @Nested
    @DisplayName("voucherSave 메소드는")
    class Describe_voucherSave {

        @Nested
        @DisplayName("바우처 타입이 null 들어와 생성과정에서 에러가 발생하면")
        class Context_with_voucher_creation_error_with_null_type {

            @Test
            @DisplayName("에러 메시지를 리턴한다")
            void it_return_error_message() {

            }
        }

        @Nested
        @DisplayName("fixed amount 바우처 타입의 amount 의 범위를 벗어난 값이 들어와 생성과정에서 에러가 발생하면")
        class Context_with_fixed_voucher_creation_error_with_out_of_range {

            @Test
            @DisplayName("에러 메시지를 리턴한다")
            void it_return_error_message() {

            }
        }

        @Nested
        @DisplayName("percent discount 바우처 타입의 percent가 범위를 벗어난 값이 들어와 생성과정에서 에러가 발생하면")
        class Context_with_voucher_creation_error_with_percent_out_of_range {

            @Test
            @DisplayName("에러 메시지를 리턴한다")
            void it_return_error_message() {

            }
        }

        @Nested
        @DisplayName("바우처가 정상적으로 생성되면")
        class Context_with_voucher_creation_success {

            @Test
            @DisplayName("생성된 바우처의 정보를 리턴한다")
            void it_return_error_message() {

            }
        }

    }
}
