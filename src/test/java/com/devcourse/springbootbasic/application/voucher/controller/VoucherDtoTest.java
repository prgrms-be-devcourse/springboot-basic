package com.devcourse.springbootbasic.application.voucher.controller;

import com.devcourse.springbootbasic.application.voucher.model.DiscountValue;
import com.devcourse.springbootbasic.application.voucher.model.Voucher;
import com.devcourse.springbootbasic.application.voucher.model.VoucherType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

class VoucherDtoTest {

    static List<VoucherDto> voucherDto = List.of(
            new VoucherDto(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, DiscountValue.from(VoucherType.FIXED_AMOUNT, 23), UUID.randomUUID()),
            new VoucherDto(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, DiscountValue.from(VoucherType.PERCENT_DISCOUNT, 41), UUID.randomUUID()),
            new VoucherDto(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, DiscountValue.from(VoucherType.FIXED_AMOUNT, 711), UUID.randomUUID())
    );

    static Stream<Arguments> provideVoucherDto() {
        return voucherDto.stream()
                .map(Arguments::of);
    }

    static Stream<Arguments> provideVoucher() {
        return voucherDto.stream()
                .map(VoucherDto::to)
                .map(Arguments::of);
    }

    @ParameterizedTest
    @DisplayName("Dto 에서 Domain 으로 변환하면 성공한다.")
    @MethodSource("provideVoucher")
    void of_ParamVoucher_ReturnVoucherDto(Voucher voucher) {
        var dto = VoucherDto.of(voucher);
        assertThat(dto, instanceOf(VoucherDto.class));
        assertThat(dto.voucherType(), is(voucher.getVoucherType()));
    }

    @ParameterizedTest
    @DisplayName("Domain 에서 Dto 로 변환하면 성공한다.")
    @MethodSource("provideVoucherDto")
    void to_ParamVoucherDto_ReturnVoucher(VoucherDto voucherDto) {
        var entity = VoucherDto.to(voucherDto);
        assertThat(entity, instanceOf(Voucher.class));
        assertThat(entity.getVoucherId(), is(voucherDto.voucherId()));
    }

}