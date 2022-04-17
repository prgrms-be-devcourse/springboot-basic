package org.prgrms.voucher.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class VoucherControllerTest {

    @Nested
    @DisplayName("Controller create 메서드는")
    class DescribeCreate{

        @Nested
        @DisplayName("바우처 타입을 파라미터로 받으면")
        class ContextReceiveVoucherTypeAndValue{

            @Test
            @DisplayName("Service 의 create 메서드에 파라미터를 넘겨주며 호출한다.")
            void itCallCreateService(){

            }

            @Test
            @DisplayName("생성된 바우처를 리턴한다.")
            void itReturnVoucher() {

            }
        }

        @Nested
        @DisplayName("전달받은 인자가 null이면")
        class ContextReceiveNull{

            @Test
            @DisplayName("에러메시지를 출력한다.")
            void itPrintNullError(){

            }
        }

        @Nested
        @DisplayName("전달 받은 인자가 공백, 빈문자열이면")
        class ContextWhiteSpace{

            @Test
            @DisplayName("에러메시지를 출력한다.")
            void itPrintWhiteSpaceError(){

            }
        }
    }
}
