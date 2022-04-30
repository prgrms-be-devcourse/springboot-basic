package com.dojinyou.devcourse.voucherapplication.voucher;

import com.dojinyou.devcourse.voucherapplication.VoucherApplication;
import com.dojinyou.devcourse.voucherapplication.voucher.domain.*;
import com.dojinyou.devcourse.voucherapplication.voucher.dto.VoucherRequest;
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
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = VoucherApplication.class)
class VoucherServiceTest {
    private static final String ERROR_MESSAGE_ABOUT_REFLEXTION = "reflextion 과정에서 에러가 발생하였습니다.\n";

    @Autowired
    VoucherService voucherService;

    @MockBean
    MemoryVoucherRepository voucherRepository;

    @Nested
    @DisplayName("Create mehotd에 관하여")
    class Describe_create_method {
        @Nested
        @DisplayName("잘못된 DTO가 들어온다면,")
        class Context_Illegal_VoucherCreateDTo {

            @ParameterizedTest
            @NullSource
            @DisplayName("예외를 발생시킨다.")
            void it_throws_Exception(Voucher voucher) {
                // given

                // when
                Throwable thrown = catchThrowable(() -> voucherService.create(voucher));

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
                VoucherRequest voucherRequest = new VoucherRequest(voucherType, VoucherAmount.of(voucherType, voucherAmount));

                Voucher voucherWithNull = VoucherMapper.requestDtoToDomain(voucherRequest);

                Long id = 999_999_999L;
                try {
                    Method getDomainMethod = VoucherMapper.class.getDeclaredMethod("getDomain", Long.class, VoucherType.class, VoucherAmount.class);
                    getDomainMethod.setAccessible(true);
                    Voucher voucherWithId = (Voucher) getDomainMethod.invoke(null, id, voucherWithNull.getVoucherType(), voucherWithNull.getVoucherAmount());

                    when(voucherRepository.create(any())).thenReturn(voucherWithId);
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    fail(ERROR_MESSAGE_ABOUT_REFLEXTION + e.getMessage());
                }

                // when
                voucherService.create(voucherWithNull);

                // then
                verify(voucherRepository, atLeastOnce()).create(any());
            }

            @ParameterizedTest
            @EnumSource(VoucherType.class)
            @DisplayName("ResponseVoucherDto를 가진 Response를 return한다.")
            void it_throws_Exception(VoucherType voucherType) {
                // given

                int voucherAmount = 50;
                VoucherRequest voucherRequest = new VoucherRequest(voucherType, VoucherAmount.of(voucherType, voucherAmount));

                Voucher voucherWithNull = VoucherMapper.requestDtoToDomain(voucherRequest);

                Long id = 999_999_999L;
                Voucher voucherWithId = null;
                try {
                    Method getDomainMethod = VoucherMapper.class.getDeclaredMethod("getDomain", Long.class, VoucherType.class, VoucherAmount.class);
                    getDomainMethod.setAccessible(true);
                    voucherWithId = (Voucher) getDomainMethod.invoke(null, id, voucherWithNull.getVoucherType(), voucherWithNull.getVoucherAmount());
                } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
                    fail(ERROR_MESSAGE_ABOUT_REFLEXTION + e.getMessage());
                }

                when(voucherRepository.create(any())).thenReturn(voucherWithId);

                // when
                Voucher savedVoucher = voucherService.create(voucherWithNull);

                // then
                assertThat(savedVoucher).isNotNull();
                assertThat(savedVoucher.getVoucherId()).isEqualTo(voucherWithId.getVoucherId());
                assertThat(savedVoucher.getVoucherType()).isEqualTo(voucherWithId.getVoucherType());
                assertThat(savedVoucher.getVoucherAmount()).isEqualTo(voucherWithId.getVoucherAmount());
                verify(voucherRepository, atLeastOnce()).create(any());
            }
        }
    }

    @Nested
    @DisplayName("List mehotd에 관하여")
    class Describe_list_method {
        @Nested
        @DisplayName("함수가 호출되었을 때,")
        class Context_MethodCall {

            @Test
            @DisplayName("Voucher Repository의 findAll 함수를 호출한다.")
            void it_Call_of_VoucherRepository_findAll_method() {
                // given
                when(voucherRepository.findAll()).thenReturn(Arrays.asList(new Voucher[]{}));

                // when
                voucherService.findAll();

                // then
                verify(voucherRepository, atLeastOnce()).findAll();


            }

            @Test
            @DisplayName("List<Voucher>를 가진 Response를 return한다.")
            void it_throws_Exception() {
                // given
                when(voucherRepository.findAll()).thenReturn(Arrays.asList(new Voucher[]{}));

                // when
                List<Voucher> response = voucherService.findAll();

                // then
                assertThat(response).isNotNull();
            }
        }
    }
}