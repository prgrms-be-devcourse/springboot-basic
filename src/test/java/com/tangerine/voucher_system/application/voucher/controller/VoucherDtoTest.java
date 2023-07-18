package com.tangerine.voucher_system.application.voucher.controller;

import com.tangerine.voucher_system.application.voucher.model.DiscountValue;
import com.tangerine.voucher_system.application.voucher.model.Voucher;
import com.tangerine.voucher_system.application.voucher.model.VoucherType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;


class VoucherDtoTest {

    static List<VoucherDto> voucherDto = List.of(
            new VoucherDto(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, new DiscountValue(VoucherType.FIXED_AMOUNT, 23), LocalDate.now()),
            new VoucherDto(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, new DiscountValue(VoucherType.PERCENT_DISCOUNT, 41), LocalDate.now()),
            new VoucherDto(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, new DiscountValue(VoucherType.FIXED_AMOUNT, 711), LocalDate.now())
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
        VoucherDto dto = VoucherDto.of(voucher);

        assertThat(dto).isInstanceOf(VoucherDto.class);
        Assertions.assertThat(dto.voucherType()).isEqualTo(voucher.getVoucherType());
    }

    @ParameterizedTest
    @DisplayName("Domain 에서 Dto 로 변환하면 성공한다.")
    @MethodSource("provideVoucherDto")
    void to_ParamVoucherDto_ReturnVoucher(VoucherDto voucherDto) {
        Voucher entity = voucherDto.to();

        assertThat(entity).isInstanceOf(Voucher.class);
        assertThat(entity.getVoucherId()).isEqualTo(voucherDto.voucherId());
    }

}