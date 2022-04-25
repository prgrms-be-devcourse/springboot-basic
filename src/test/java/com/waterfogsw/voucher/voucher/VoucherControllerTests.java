package com.waterfogsw.voucher.voucher;

import com.waterfogsw.voucher.voucher.controller.VoucherController;
import com.waterfogsw.voucher.voucher.domain.FixedAmountVoucher;
import com.waterfogsw.voucher.voucher.domain.Voucher;
import com.waterfogsw.voucher.voucher.domain.VoucherType;
import com.waterfogsw.voucher.voucher.dto.ResponseStatus;
import com.waterfogsw.voucher.voucher.dto.RequestVoucherDto;
import com.waterfogsw.voucher.voucher.service.VoucherService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

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
        @DisplayName("null 인 dto 가 들어오면")
        class Context_with_type_null {

            @Test
            @DisplayName("BadRequest Status 를 가진 응답을 리턴한다")
            void it_return_error_message() {
                var response = controller.voucherAdd(null);

                assertThat(response.status(), is(ResponseStatus.BAD_REQUEST));
            }
        }

        @Nested
        @DisplayName("바우처가 정상적으로 생성되면")
        class Context_with_all_argument_not_null {

            @Test
            @DisplayName("생성된 바우처의 정보를 가진 응답을 리턴한다")
            void it_return_error_message() {
                var voucherDto = new RequestVoucherDto(VoucherType.FIXED_AMOUNT, 100);

                when(voucherService.saveVoucher(any(Voucher.class)))
                        .thenReturn(voucherDto.toDomain());

                var response = controller.voucherAdd(voucherDto);

                assertThat(response.status(), is(ResponseStatus.OK));
                assertThat(response.body().type(), is(voucherDto.type()));
                assertThat(response.body().value(), is(voucherDto.value()));
            }
        }
    }

    @Nested
    @DisplayName("voucherList 메소드는")
    class Describe_voucher {
        @Nested
        @DisplayName("조회 서비스로 부터 모든 바우처에 대한 정보가 리턴되면")
        class Context_with_get_voucher_list {
            @Test
            @DisplayName("바우처 DTO 리스트를 가진 응답을 리턴한다")
            void it_return_dto_list() {
                List<Voucher> voucherList = new ArrayList<>();
                Voucher voucher1 = new FixedAmountVoucher(100);
                Voucher voucher2 = new FixedAmountVoucher(100);

                voucherList.add(voucher1);
                voucherList.add(voucher2);

                when(voucherService.findAllVoucher()).thenReturn(voucherList);

                var response = controller.voucherList();

                assertThat(response.status(), is(ResponseStatus.OK));
                assertThat(response.body().size(), is(2));
            }
        }
    }
}
