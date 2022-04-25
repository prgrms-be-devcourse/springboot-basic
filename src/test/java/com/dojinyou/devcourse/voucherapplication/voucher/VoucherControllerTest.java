package com.dojinyou.devcourse.voucherapplication.voucher;

import com.dojinyou.devcourse.voucherapplication.Response;
import com.dojinyou.devcourse.voucherapplication.VoucherApplication;
import com.dojinyou.devcourse.voucherapplication.voucher.domain.*;
import com.dojinyou.devcourse.voucherapplication.voucher.dto.VoucherRequestDto;
import com.dojinyou.devcourse.voucherapplication.voucher.dto.VoucherResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = VoucherApplication.class)
class VoucherControllerTest {
    @Autowired
    VoucherController voucherController;

    @SpyBean
    VoucherService voucherService;

    @Nested
    @DisplayName("Create mehotd에 관하여")
    class Describe_create_method {
        @Nested
        @DisplayName("잘못된 DTO가 들어온다면,")
        class Context_Illegal_VoucherCreateDTo {

            @ParameterizedTest
            @NullSource
            @DisplayName("예외를 발생시킨다.")
            void it_throws_Exception(VoucherRequestDto voucherRequestDto) {
                // given

                // when
                Throwable thrown = catchThrowable(()->voucherController.create(voucherRequestDto));

                // then
                assertThat(thrown).isNotNull();
                assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
            }

            @Test
            @DisplayName("예외를 발생시킨다.")
            void it_throws_Exception() {
                // given
                VoucherRequestDto voucherRequestDto = new VoucherRequestDto(null, null);

                // when
                Throwable thrown = catchThrowable(()->voucherController.create(voucherRequestDto));

                // then
                assertThat(thrown).isNotNull();
                assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
            }

        }
        @Nested
        @DisplayName("정상적인 DTO가 들어온다면,")
        class Context_Correct_VoucherCreateDTo {

            @ParameterizedTest
            @EnumSource(VoucherType.class)
            @DisplayName("Voucher Service의 create 함수를 호출한다.")
            void it_Call_of_VoucherService_create_method(VoucherType voucherType) {
                // given
                int voucherAmount = 50;
                VoucherRequestDto voucherRequestDto = new VoucherRequestDto(voucherType, VoucherAmount.of(voucherType, voucherAmount));

                // when
                voucherController.create(voucherRequestDto);

                // then
                verify(voucherService,atLeastOnce()).create(voucherRequestDto);

            }

            @ParameterizedTest
            @EnumSource(VoucherType.class)
            @DisplayName("생성된 Voucher를 return한다.")
            void it_throws_Exception(VoucherType voucherType) {
                // given
                int voucherAmount = 50;
                VoucherRequestDto voucherRequestDto = new VoucherRequestDto(voucherType, VoucherAmount.of(voucherType, voucherAmount));
                long id = 9999L;
                VoucherResponseDto voucherResponseDto = new VoucherResponseDto(id, voucherRequestDto.getVoucherType(), voucherRequestDto.getVoucherAmount());
                Response<VoucherResponseDto> expectedResponse = new Response<>(Response.State.SUCCESS, voucherResponseDto);
                when(voucherService.create(voucherRequestDto)).thenReturn(expectedResponse);

                // when
                Response<VoucherResponseDto> response = voucherController.create(voucherRequestDto);

                // then
                assertThat(response).isNotNull();
                assertThat(response).isEqualTo(expectedResponse);
            }
        }
    }

}