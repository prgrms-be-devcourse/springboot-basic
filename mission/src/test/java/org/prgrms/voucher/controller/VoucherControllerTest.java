package org.prgrms.voucher.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgrms.voucher.exceptions.NullReceiveException;
import org.prgrms.voucher.models.Voucher;
import org.prgrms.voucher.service.VoucherService;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VoucherControllerTest {

    @Nested
    @DisplayName("Controller create 메서드는")
    class DescribeCreate {

        @Mock
        VoucherService voucherServiceMock;
        @InjectMocks
        VoucherController voucherController;

        @Nested
        @DisplayName("create 기능을 테스트 할 떄 바우처 타입을 파라미터로 받으면")
        class ContextReceiveVoucherTypeAndValue {

            Voucher voucher;

            @Test
            @DisplayName("Service 의 create 메서드에 파라미터를 넘겨주며 호출한다.")
            void itCallCreateService() {

                voucherController.create(voucher);

                verify(voucherServiceMock).create(voucher);
            }

            @Test
            @DisplayName("생성된 바우처를 리턴한다.")
            void itReturnVoucher() {

                when(voucherServiceMock.create(voucher)).thenReturn(voucher);

                Voucher voucherCheck = voucherController.create(voucher);

                Assertions.assertThat(voucher).isEqualTo(voucherCheck);
            }
        }

        @Nested
        @DisplayName("create 기능을 테스트 할 때 전달받은 바우처타입 인자가 null이면")
        class ContextReceiveNull {

            Voucher voucher = null;

            @Test
            @DisplayName("null 에러메시지를 출력한다.")
            void itPrintNullError() {

                Assertions.assertThatThrownBy(() -> voucherController.create(voucher))
                        .isInstanceOf(NullReceiveException.class)
                        .hasMessage("null create request...");
            }
        }

//        @Nested
//        @DisplayName("crete 기능을 테스트 할 때 전달 받은 바우처타입 인자가 공백, 빈문자열이면")
//        class ContextWhiteSpace{
//
//
//            @Test
//            @DisplayName("공백 에러메시지를 출력한다.")
//            void itPrintWhiteSpaceError(){
//
//            }
//        }
    }
}
