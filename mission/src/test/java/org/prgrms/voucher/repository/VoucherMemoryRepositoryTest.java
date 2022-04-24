package org.prgrms.voucher.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class VoucherMemoryRepositoryTest {

    @Nested
    @DisplayName("Repository save 메서드는")
    class DescribeCreate {

        @Nested
        @DisplayName("save 기능을 테스트 할 때 바우처 객체를 null로 받을 때")
        class ContextReceiveFixedVoucherTypeAndWrongValue {

            @Test
            @DisplayName("잘못된 인자 예외를 던진다.")
            void itIllegalArgumentExceptionThrow() {

            }
        }

        @Nested
        @DisplayName("save 기능을 테스트 할 때 바우처 객체를 인자로 받으면")
        class ContextReceiveNullVoucherType {

            @Test
            @DisplayName("해시맵 저장소에 저장하고 저장한 바우처를 반환한다.")
            void itIllegalArgumentExceptionThrow() {

            }
        }
    }
}

