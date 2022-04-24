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
        @DisplayName("repository 에서 NPE 가 발생하면")
        class Context_with_negative_fixedAmount {

            @Test
            @DisplayName("RepositoryException 예외를 발생시킨다")
            void it_throw_RepositoryException() {

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
