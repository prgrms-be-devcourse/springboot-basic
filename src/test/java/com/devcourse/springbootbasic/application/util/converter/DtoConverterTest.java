package com.devcourse.springbootbasic.application.util.converter;

import com.devcourse.springbootbasic.application.domain.Voucher;
import com.devcourse.springbootbasic.application.dto.VoucherDto;
import com.devcourse.springbootbasic.application.dto.VoucherType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

class DtoConverterTest {

    @ParameterizedTest
    @DisplayName("Dto가 Domain으로 전환되어서 반환되면 성공")
    @MethodSource("provideVoucherDto")
    void dto변환테스트(VoucherDto voucherDto) {
        var voucher = DtoConverter.convertDtoToDomain(voucherDto);
        assertThat(voucher, isA(Voucher.class));
        assertThat(voucher.getVoucherType(), is(voucherDto.voucherType()));
    }

    static Stream<Arguments> provideVoucherDto() {
        return Stream.of(
                Arguments.of(new VoucherDto(VoucherType.PERCENT_DISCOUNT, 23)),
                Arguments.of(new VoucherDto(VoucherType.FIXED_AMOUNT, 213)),
                Arguments.of(new VoucherDto(VoucherType.FIXED_AMOUNT, 100))
        );
    }

}