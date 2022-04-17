package org.prgrms.voucher.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class VoucherControllerTest {

    @Nested
    @DisplayName("Controller create 메서드는")
    class DescribeCreate{

        @Nested
        @DisplayName("사용자에게 바우처 타입과 할인값을 받으면")
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
    }
}
