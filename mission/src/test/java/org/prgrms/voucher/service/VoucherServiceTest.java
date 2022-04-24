package org.prgrms.voucher.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class VoucherServiceTest {

    @Nested
    @DisplayName("Service create 메서드는")
    class DescribeCreate {

        @Nested
        @DisplayName("create 기능을 테스트 할 때 바우처 타입 Fixed, 할인값 -100을 인자로 받으면")
        class ContextReceiveFixedVoucherTypeAndWrongValue {

            @Test
            @DisplayName("잘못된 할인값 메시지와 예외를 던진다.")
            void itIllegalArgumentExceptionThrow() {

            }
        }

        @Nested
        @DisplayName("create 기능을 테스트 할 때 바우처 타입이 null이면")
        class ContextReceiveNullVoucherType {

            @Test
            @DisplayName("잘못된 타입 메시지와 예외를 던진다.")
            void itIllegalArgumentExceptionThrow() {

            }
        }

        @Nested
        @DisplayName("create 기능을 테스트할 때 바우처 타입 Fixed, 할인값 100을 인자로 받으면")
        class ContextReceiveFixedVoucherTypeAndValue {

            @Test
            @DisplayName("저장하기 위해 바우처 repository의 save 메서드를 호출한다.")
            void itCallRepositorySave() {

            }

            @Test
            @DisplayName("생성된 바우처를 반환한다.")
            void itCreateVoucherAndReturn() {

            }
        }
    }
}
