package com.waterfogsw.voucher.voucher;

import com.waterfogsw.voucher.voucher.controller.ErrorMessages;
import com.waterfogsw.voucher.voucher.controller.VoucherController;
import com.waterfogsw.voucher.voucher.controller.VoucherDto;
import com.waterfogsw.voucher.voucher.domain.Voucher;
import com.waterfogsw.voucher.voucher.domain.VoucherType;
import com.waterfogsw.voucher.voucher.service.VoucherService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VoucherControllerTests {

    @Mock
    private VoucherService voucherService;

    @InjectMocks
    private VoucherController controller;

    @Nested
    @DisplayName("voucherSave 메소드는")
    class Describe_voucherSave {

        @Nested
        @DisplayName("바우처 타입이 null 들어오면")
        class Context_with_type_null {

            @Test
            @DisplayName("에러 메시지를 리턴한다")
            void it_return_error_message() {
                VoucherDto.Request request = new VoucherDto.Request(null, 1000d);

                VoucherDto.Response response = controller.voucherSave(request);

                assertThat(response.getReturnMessage(), is(ErrorMessages.INVALID_VOUCHER_TYPE));
            }
        }

        @Nested
        @DisplayName("value 가 null 이 들어오면")
        class Context_with_value_null {

            @Test
            @DisplayName("에러 메시지를 리턴한다")
            void it_return_error_message() {
                VoucherDto.Request request = new VoucherDto.Request(VoucherType.FIXED_AMOUNT, null);

                VoucherDto.Response response = controller.voucherSave(request);

                assertThat(response.getReturnMessage(), is(ErrorMessages.OUT_OF_RANGE));
            }
        }

        @Nested
        @DisplayName("바우처가 정상적으로 생성되면")
        class Context_with_all_argument_not_null {
            @Captor
            ArgumentCaptor<Voucher> voucherCaptor;

            @Test
            @DisplayName("생성된 바우처의 정보를 리턴한다")
            void it_return_error_message() {
                VoucherDto.Request request = new VoucherDto.Request(VoucherType.FIXED_AMOUNT, 5000d);
                when(voucherService.saveVoucher(voucherCaptor.capture())).thenReturn(voucherCaptor.getValue());

                VoucherDto.Response response = controller.voucherSave(request);

                VoucherDto.Info voucherInfo = response.getInfo();
                assertThat(voucherInfo.getId(), is(voucherCaptor.getValue().getId()));
                assertThat(voucherInfo.getType(), is(voucherCaptor.getValue().getType()));
                assertThat(voucherInfo.getValue(), is(voucherCaptor.getValue().getValue()));
            }
        }
    }
}
