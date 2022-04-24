package org.prgrms.voucher.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgrms.voucher.dto.VoucherDto;
import org.prgrms.voucher.models.FixedAmountVoucher;
import org.prgrms.voucher.models.Voucher;
import org.prgrms.voucher.models.VoucherType;
import org.prgrms.voucher.response.Response;
import org.prgrms.voucher.response.ResponseState;
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
        @DisplayName("create 기능을 테스트 할 때 바우처 타입 1, 할인값 100을 인자로 받으면")
        class ContextReceiveVoucherTypeAndValue {

            VoucherDto.VoucherRequest requestDto = new VoucherDto.VoucherRequest(100, VoucherType.FIXED_AMOUNT);

            Voucher voucher = new FixedAmountVoucher(1L,100, VoucherType.FIXED_AMOUNT);

            @Test
            @DisplayName("Service 의 create 메서드에 파라미터를 넘겨주며 호출한다.")
            void itCallCreateService() {

                when(voucherServiceMock.create(requestDto)).thenReturn(voucher);

                voucherController.create(requestDto);

                verify(voucherServiceMock).create(requestDto);
            }

            @Test
            @DisplayName("바우처가 생성되면 생성된 정보를 리턴한다.")
            void itReturnVoucherResponse() {

                when(voucherServiceMock.create(requestDto)).thenReturn(voucher);

                Response response = voucherController.create(requestDto);

                VoucherDto.VoucherResponse responseCheck = VoucherDto.VoucherResponse.from(voucher);

                Assertions.assertThat(response.data()).isEqualTo(responseCheck);
            }
        }

        @Nested
        @DisplayName("create 기능을 테스트 할 때 전달받은 바우처타입 인자가 null이면")
        class ContextReceiveNull {

            VoucherDto.VoucherRequest requestDto = null;

            @Test
            @DisplayName("실패 상태와 재입력 메시지를 반환한다.")
            void itPrintNullError() {

                Response response = voucherController.create(requestDto);
                String message = "please retry.";

                Assertions.assertThat(response.state()).isEqualTo(ResponseState.BAD_REQUEST);
                Assertions.assertThat(response.data()).isEqualTo(message);
            }
        }
    }
}
