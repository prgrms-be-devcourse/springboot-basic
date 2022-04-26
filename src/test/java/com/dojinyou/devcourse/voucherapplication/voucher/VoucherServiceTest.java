package com.dojinyou.devcourse.voucherapplication.voucher;

import com.dojinyou.devcourse.voucherapplication.VoucherApplication;
import com.dojinyou.devcourse.voucherapplication.voucher.domain.Voucher;
import com.dojinyou.devcourse.voucherapplication.voucher.domain.VoucherAmount;
import com.dojinyou.devcourse.voucherapplication.voucher.domain.VoucherMapper;
import com.dojinyou.devcourse.voucherapplication.voucher.domain.VoucherType;
import com.dojinyou.devcourse.voucherapplication.voucher.dto.VoucherRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = VoucherApplication.class)
class VoucherServiceTest {
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
            void it_Call_of_VoucherService_create_method(VoucherType voucherType) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
                // given
                int voucherAmount = 50;
                VoucherRequest voucherRequest = new VoucherRequest(voucherType, VoucherAmount.of(voucherType, voucherAmount));

                Voucher voucherWithNull = VoucherMapper.requestDtoToDomain(voucherRequest);

                Long id = 999_999_999L;
                Method getDomainMethod = VoucherMapper.class.getDeclaredMethod("getDomain", Long.class, VoucherType.class, VoucherAmount.class);
                getDomainMethod.setAccessible(true);
                Voucher voucherWithId = (Voucher) getDomainMethod.invoke(null, id, voucherWithNull.getVoucherType(), voucherWithNull.getVoucherAmount());

                when(voucherRepository.create(any())).thenReturn(voucherWithId);

                // when
                voucherService.create(voucherWithNull);

                // then
                verify(voucherRepository, atLeastOnce()).create(any());
            }

            @ParameterizedTest
            @EnumSource(VoucherType.class)
            @DisplayName("ResponseVoucherDto를 가진 Response를 return한다.")
            void it_throws_Exception(VoucherType voucherType) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
                // given

                int voucherAmount = 50;
                VoucherRequest voucherRequest = new VoucherRequest(voucherType, VoucherAmount.of(voucherType, voucherAmount));

                Voucher voucherWithNull = VoucherMapper.requestDtoToDomain(voucherRequest);

                Long id = 999_999_999L;
                Method getDomainMethod = VoucherMapper.class.getDeclaredMethod("getDomain", Long.class, VoucherType.class, VoucherAmount.class);
                getDomainMethod.setAccessible(true);
                Voucher voucherWithId = (Voucher) getDomainMethod.invoke(null, id, voucherWithNull.getVoucherType(), voucherWithNull.getVoucherAmount());

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
}