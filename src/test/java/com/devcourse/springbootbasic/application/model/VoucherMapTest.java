package com.devcourse.springbootbasic.application.model;

import com.devcourse.springbootbasic.application.domain.voucher.Voucher;
import com.devcourse.springbootbasic.application.vo.VoucherMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class VoucherMapTest {

    VoucherMap voucherMap;

    @BeforeEach
    void init() {
        voucherMap = new VoucherMap(new HashMap<>());
    }

    @ParameterizedTest
    @DisplayName("정상적인 값(UUID, Voucher) 넣으면 바우처 생성 성공한다.")
    @MethodSource("provideValids")
    void addVoucher_ParamVoucher_InsertAndReturnVoucher(Voucher voucher) {
        voucherMap.addVoucher(voucher);
        assertThat(voucherMap.getAllVouchers(), not(empty()));
    }

    @ParameterizedTest
    @DisplayName("비정상적인 값 넣으면 바우처 생성 실패한다.")
    @MethodSource("provideInvalids")
    void addVoucher_ParamWrongVoucher_Exception(Object input) {
        assertThrows(Exception.class, () -> voucherMap.addVoucher((Voucher) input));
    }

    @Test
    @DisplayName("생성된 모든 바우처를 리스트 형태로 반환한다.")
    void getAllVouchers_VoucherMap_ReturnVoucherList() {
        var list = voucherMap.getAllVouchers();
        assertThat(list, notNullValue());
    }

    static Stream<Arguments> provideValids() {
        return Stream.of(
                Arguments.of(new Voucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, new DiscountValue(VoucherType.FIXED_AMOUNT, "100"))),
                Arguments.of(new Voucher(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT, new DiscountValue(VoucherType.PERCENT_DISCOUNT, "13"))),
                Arguments.of(new Voucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, new DiscountValue(VoucherType.FIXED_AMOUNT, "14")))
        );
    }

    static Stream<Arguments> provideInvalids() {
        return Stream.of(
                Arguments.of("My name is SuperMan"),
                Arguments.of(123),
                Arguments.of(List.of(12, 3))
        );
    }

}