package com.dojinyou.devcourse.voucherapplication.voucher;

import com.dojinyou.devcourse.voucherapplication.Response;
import com.dojinyou.devcourse.voucherapplication.VoucherApplication;
import com.dojinyou.devcourse.voucherapplication.voucher.domain.Voucher;
import com.dojinyou.devcourse.voucherapplication.voucher.domain.VoucherAmount;
import com.dojinyou.devcourse.voucherapplication.voucher.domain.VoucherMapper;
import com.dojinyou.devcourse.voucherapplication.voucher.domain.VoucherType;
import com.dojinyou.devcourse.voucherapplication.voucher.dto.VoucherRequest;
import com.dojinyou.devcourse.voucherapplication.voucher.dto.VoucherResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.NullSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = VoucherApplication.class)
class VoucherControllerTest {
    private static final String ERROR_MESSAGE_ABOUT_REFLEXTION = "reflextion 과정에서 에러가 발생하였습니다.\n";
    @Autowired
    VoucherController voucherController;

    @MockBean
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
            void it_throws_Exception(VoucherRequest voucherRequest) {
                // given

                // when
                Throwable thrown = catchThrowable(() -> voucherController.create(voucherRequest));

                // then
                assertThat(thrown).isNotNull();
                assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
            }

            @Test
            @DisplayName("예외를 발생시킨다.")
            void it_throws_Exception() {
                // given
                VoucherRequest voucherRequest = new VoucherRequest(null, null);

                // when
                Throwable thrown = catchThrowable(() -> voucherController.create(voucherRequest));

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
            void it_Call_of_VoucherService_create_method(VoucherType voucherType) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
                // given
                int voucherAmount = 50;
                VoucherRequest voucherRequest = new VoucherRequest(voucherType, VoucherAmount.of(voucherType, voucherAmount));

                Long id = 999_999_999L;
                Method getDomainMethod = VoucherMapper.class.getDeclaredMethod("getDomain", Long.class, VoucherType.class, VoucherAmount.class);
                getDomainMethod.setAccessible(true);
                Voucher voucherWithId = (Voucher) getDomainMethod.invoke(null, id, voucherRequest.getVoucherType(), voucherRequest.getVoucherAmount());

                when(voucherService.create(any(Voucher.class))).thenReturn(voucherWithId);

                // when
                voucherController.create(voucherRequest);

                // then
                verify(voucherService, atLeastOnce()).create(any());

            }

            @ParameterizedTest
            @EnumSource(VoucherType.class)
            @DisplayName("ResponseDto를 가진 Response를 return한다.")
            void it_throws_Exception(VoucherType voucherType) {
                // given
                int voucherAmount = 50;
                VoucherRequest voucherRequest = new VoucherRequest(voucherType, VoucherAmount.of(voucherType, voucherAmount));

                Long id = 999_999_999L;
                try {
                    Method getDomainMethod = VoucherMapper.class.getDeclaredMethod("getDomain", Long.class, VoucherType.class, VoucherAmount.class);
                    getDomainMethod.setAccessible(true);
                    Voucher voucherWithId = (Voucher) getDomainMethod.invoke(null, id, voucherRequest.getVoucherType(), voucherRequest.getVoucherAmount());

                    when(voucherService.create(any(Voucher.class))).thenReturn(voucherWithId);
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    fail(ERROR_MESSAGE_ABOUT_REFLEXTION + e.getMessage());
                }

                VoucherResponse expectedResponseDto = new VoucherResponse(id, voucherRequest.getVoucherType(), voucherRequest.getVoucherAmount());

                // when
                Response<VoucherResponse> response = voucherController.create(voucherRequest);
                VoucherResponse responseDto = response.getData();

                // then
                assertThat(response).isNotNull();
                assertThat(response.getState()).isEqualTo(Response.State.SUCCESS);
                assertThat(responseDto).isNotNull();
                assertThat(responseDto.getVoucherId()).isEqualTo(expectedResponseDto.getVoucherId());
                assertThat(responseDto.getVoucherType()).isEqualTo(expectedResponseDto.getVoucherType());
                assertThat(responseDto.getVoucherAmount()).isEqualTo(expectedResponseDto.getVoucherAmount());
            }
        }
    }

}