package com.devcourse.springbootbasic.application.voucher;

import com.devcourse.springbootbasic.application.voucher.model.Voucher;
import com.devcourse.springbootbasic.application.voucher.controller.VoucherDto;
import com.devcourse.springbootbasic.application.voucher.vo.DiscountValue;
import com.devcourse.springbootbasic.application.voucher.vo.VoucherType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class VoucherConverterTest {

    @Test
    @DisplayName("바우처 리스트를 상응하는 문자열 리스트로 변환 시 성공한다.")
    void ConvertToStringList_ParamVoucherList_ReturnVoucherString() {
        List<Voucher> vouchers = List.of(
                new Voucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, new DiscountValue(VoucherType.FIXED_AMOUNT, "100")),
                new Voucher(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT, new DiscountValue(VoucherType.PERCENT_DISCOUNT, "0")),
                new Voucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, new DiscountValue(VoucherType.FIXED_AMOUNT, "1240")),
                new Voucher(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT, new DiscountValue(VoucherType.PERCENT_DISCOUNT, "10"))
        );
        var result = VoucherConverter.convertToStringList(vouchers);
        assertThat(result, notNullValue());
        assertThat(result, not(empty()));
        assertThat(result, instanceOf(List.class));
        assertThat(result.get(0), instanceOf(String.class));
    }

    @ParameterizedTest
    @DisplayName("바우처Dto로 바우처가 생성되면 성공한다.")
    @MethodSource("provideTestSetForConvertDtoToVoucher")
    void ConvertDtoToVoucher_ParamVoucherDto_ReturnVoucher(VoucherDto voucherDto, UUID uuid) {
        var result = VoucherConverter.convertDtoToVoucher(voucherDto, uuid);
        assertThat(result, notNullValue());
        assertThat(result.getVoucherId(), is(uuid));
    }

    @ParameterizedTest
    @DisplayName("바우처 정보를 Csv 문자열로 변환하면 성공한다.")
    @MethodSource("provideVouchers")
    void ConvertVoucherToCsv_ParamVoucher_ReturnVoucherCsv(Voucher voucher) {
        var result = VoucherConverter.convertVoucherToCsv(voucher);
        assertThat(result, not(blankString()));
        assertThat(result, is(containsString(",")));
        assertThat(UUID.fromString(result.split(",")[0]), is(voucher.getVoucherId()));
    }

    @ParameterizedTest
    @DisplayName("Csv 문자열을 상응하는 바우처로 변환하면 성공한다.")
    @MethodSource("provideCsvLine")
    void ConvertCsvToVoucher_ParamVoucherCsv_ReturnVoucher(String csvLine) {
        var uuid = UUID.fromString(csvLine.split(",")[0]);
        var result = VoucherConverter.convertCsvToVoucher(csvLine);
        assertThat(result, notNullValue());
        assertThat(result.getVoucherId(), is(uuid));
    }

    static Stream<Arguments> provideTestSetForConvertDtoToVoucher() {
        return Stream.of(
                Arguments.of(new VoucherDto(VoucherType.FIXED_AMOUNT, new DiscountValue(VoucherType.FIXED_AMOUNT, "100")), UUID.randomUUID()),
                Arguments.of(new VoucherDto(VoucherType.PERCENT_DISCOUNT, new DiscountValue(VoucherType.PERCENT_DISCOUNT, "0")), UUID.randomUUID()),
                Arguments.of(new VoucherDto(VoucherType.FIXED_AMOUNT, new DiscountValue(VoucherType.FIXED_AMOUNT, "1240")), UUID.randomUUID()),
                Arguments.of(new VoucherDto(VoucherType.PERCENT_DISCOUNT, new DiscountValue(VoucherType.PERCENT_DISCOUNT, "10")), UUID.randomUUID())
        );
    }

    static Stream<Arguments> provideVouchers() {
        return Stream.of(
                Arguments.of(new Voucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, new DiscountValue(VoucherType.FIXED_AMOUNT, "100"))),
                Arguments.of(new Voucher(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT, new DiscountValue(VoucherType.PERCENT_DISCOUNT, "0"))),
                Arguments.of(new Voucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, new DiscountValue(VoucherType.FIXED_AMOUNT, "1240"))),
                Arguments.of(new Voucher(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT, new DiscountValue(VoucherType.PERCENT_DISCOUNT, "10")))
        );
    }

    static Stream<Arguments> provideCsvLine() {
        return Stream.of(
                Arguments.of("f2e4ed50-d3a2-428c-aa90-b2159b9ebd2e,FIXED_AMOUNT,10"),
                Arguments.of("394cb7c9-d6ae-4259-a3c5-801eff2c0526,PERCENT_DISCOUNT,100"),
                Arguments.of("8fd4d2ae-fb6e-4d08-be0f-10c3e1781ccd,FIXED_AMOUNT,100"),
                Arguments.of("f6d6fee2-044a-4f0a-87c5-632b35b97855,PERCENT_DISCOUNT,0")
        );
    }

}