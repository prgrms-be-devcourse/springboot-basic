package com.waterfogsw.voucher.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class VoucherControllerTests {
    @Nested
    @DisplayName("create 메소드는")
    class Describe_create {

        @Nested
        @DisplayName("인자가 잘못되어 바우처 생성과정에서 에러가 발생하면")
        class Context_with_voucher_creation_error {

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
