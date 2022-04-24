package com.waterfogsw.voucher.voucher;

import com.waterfogsw.voucher.voucher.controller.VoucherController;
import com.waterfogsw.voucher.voucher.domain.Voucher;
import com.waterfogsw.voucher.voucher.domain.VoucherType;
import com.waterfogsw.voucher.voucher.dto.ResponseStatus;
import com.waterfogsw.voucher.voucher.dto.VoucherDto;
import com.waterfogsw.voucher.voucher.service.VoucherService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VoucherControllerTests {

    @Mock
    private VoucherService voucherService;

    @InjectMocks
    private VoucherController controller;

    @Nested
    @DisplayName("voucherAdd 메소드는")
    class Describe_voucherSave {

        @Nested
        @DisplayName("바우처 타입이 null 들어오면")
        class Context_with_type_null {

            @Test
            @DisplayName("BadRequest Status 를 가진 응답을 리턴한다")
            void it_return_error_message() {
                var voucherDto = new VoucherDto(null, 1000);
                var response = controller.voucherAdd(voucherDto);

                assertThat(response.status(), is(ResponseStatus.BAD_REQUEST));
            }
        }

        @Nested
        @DisplayName("value 값이 0 이 들어오면")
        class Context_with_value_zero {

            @Test
            @DisplayName("BadRequest Status 를 가진 응답을 리턴한다")
            void it_return_error_message() {
                var voucherDto = new VoucherDto(VoucherType.FIXED_AMOUNT, 0);
                var response = controller.voucherAdd(voucherDto);

                assertThat(response.status(), is(ResponseStatus.BAD_REQUEST));
            }
        }

        @Nested
        @DisplayName("바우처가 정상적으로 생성되면")
        class Context_with_all_argument_not_null {

            @Test
            @DisplayName("생성된 바우처의 정보를 가진 응답을 리턴한다")
            void it_return_error_message() {
                var voucherDto = new VoucherDto(VoucherType.FIXED_AMOUNT, 100);

                when(voucherService.saveVoucher(any(Voucher.class)))
                        .thenReturn(voucherDto.toDomain());

                var response = controller.voucherAdd(voucherDto);

                assertThat(response.status(), is(ResponseStatus.OK));
                assertThat(response.body().type(), is(voucherDto.type()));
                assertThat(response.body().value(), is(voucherDto.value()));
            }
        }
    }
}
