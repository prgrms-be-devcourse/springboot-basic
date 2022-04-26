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
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.concurrent.atomic.AtomicLong;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = VoucherApplication.class)
class MemoryVoucherRepositoryTest {
    @Autowired()
    MemoryVoucherRepository voucherRepository;

    @Nested
    @DisplayName("Create mehotd에 관하여")
    class Describe_create_method {
        @Nested
        @DisplayName("잘못된 domain이 들어온다면,")
        class Context_Illegal_VoucherCreateDTo {

            @ParameterizedTest
            @NullSource
            @DisplayName("예외를 발생시킨다.")
            void it_throws_Exception(Voucher voucher) {
                // given

                // when
                Throwable thrown = catchThrowable(() -> voucherRepository.create(voucher));

                // then
                assertThat(thrown).isNotNull();
                assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
            }
        }

        @Nested
        @DisplayName("정상적인 domain가 들어온다면,")
        class Context_Correct_VoucherCreateDTo {

            @ParameterizedTest
            @EnumSource(VoucherType.class)
            @DisplayName("id를 가진 Voucher를 return한다.")
            void it_throws_Exception(VoucherType voucherType) throws NoSuchFieldException, IllegalAccessException {
                // given
                int amount = 50;
                VoucherAmount voucherAmount = VoucherAmount.of(voucherType, amount);
                Voucher voucher = VoucherMapper.requestDtoToDomain(new VoucherRequest(voucherType, voucherAmount));
                Field idGeneratorField = voucherRepository.getClass().getDeclaredField("idGenerator");
                Long initialId = 100L;
                setFinalStatic(idGeneratorField, new AtomicLong(initialId));

                // when
                Voucher savedVoucher = voucherRepository.create(voucher);

                // then
                assertThat(savedVoucher).isNotNull();
                assertThat(savedVoucher.getVoucherId()).isEqualTo(initialId + 1);
                assertThat(savedVoucher.getVoucherType()).isEqualTo(voucherType);
                assertThat(savedVoucher.getVoucherAmount()).isEqualTo(voucherAmount);
            }
        }
    }

    static void setFinalStatic(Field field, Object newValue) throws NoSuchFieldException, IllegalAccessException {
        field.setAccessible(true);

        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

        field.set(null, newValue);
    }
}