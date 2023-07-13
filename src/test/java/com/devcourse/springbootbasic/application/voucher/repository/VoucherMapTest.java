package com.devcourse.springbootbasic.application.voucher.repository;

import com.devcourse.springbootbasic.application.global.exception.InvalidDataException;
import com.devcourse.springbootbasic.application.voucher.model.DiscountValue;
import com.devcourse.springbootbasic.application.voucher.model.Voucher;
import com.devcourse.springbootbasic.application.voucher.model.VoucherType;
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

    static Stream<Arguments> provideValid() {
        return Stream.of(
                Arguments.of(new Voucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, new DiscountValue(VoucherType.FIXED_AMOUNT, "100"), UUID.randomUUID())),
                Arguments.of(new Voucher(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT, new DiscountValue(VoucherType.PERCENT_DISCOUNT, "13"), UUID.randomUUID())),
                Arguments.of(new Voucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, new DiscountValue(VoucherType.FIXED_AMOUNT, "14"), UUID.randomUUID()))
        );
    }

    static Stream<Arguments> provideInvalids() {
        return Stream.of(
                Arguments.of("My name is SuperMan"),
                Arguments.of(123),
                Arguments.of(List.of(12, 3))
        );
    }

    @BeforeEach
    void init() {
        voucherMap = new VoucherMap(new HashMap<>());
    }

    @ParameterizedTest
    @DisplayName("정상적인 값(UUID, Voucher) 넣으면 바우처 생성 성공한다.")
    @MethodSource("provideValid")
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

    @ParameterizedTest
    @DisplayName("존재하는 바우처 넣으면 바우처 추가 성공한다.")
    @MethodSource("provideValid")
    void addIfVoucherExist_ParamExistVoucher_AddVoucher(Voucher voucher) {
        voucherMap.addVoucher(voucher);
        var newVoucher = new Voucher(voucher.getVoucherId(), voucher.getVoucherType(), new DiscountValue(voucher.getVoucherType(), 1), UUID.randomUUID());
        voucherMap.addIfVoucherExist(newVoucher);
        var foundVoucher = voucherMap.getVoucherById(voucher.getVoucherId());
        assertThat(foundVoucher, samePropertyValuesAs(newVoucher));
    }

    @Test
    @DisplayName("생성된 모든 바우처를 리스트 형태로 반환한다.")
    void getAllVouchers_VoucherMap_ReturnVoucherList() {
        var list = voucherMap.getAllVouchers();
        assertThat(list, notNullValue());
    }

    @Test
    @DisplayName("생성된 바우처를 아이디로 찾으면 성공한다.")
    void getVoucherById_ParamExistVoucher_ReturnVoucherOrNull() {
        var voucherId = UUID.randomUUID();
        var voucherId2 = UUID.randomUUID();
        var voucher = new Voucher(voucherId, VoucherType.FIXED_AMOUNT, new DiscountValue(VoucherType.FIXED_AMOUNT, 10), UUID.randomUUID());
        voucherMap.addVoucher(voucher);
        var findedVoucher = voucherMap.getVoucherById(voucherId);
        var maybeNull = voucherMap.getVoucherById(voucherId2);
        assertThat(findedVoucher, samePropertyValuesAs(voucher));
        assertThat(maybeNull, nullValue());
    }

    @Test
    @DisplayName("모든 바우처를 제거하면 성공한다.")
    void clearVoucherMap_ParamVoid_ClearVoucherMap() {
        voucherMap.clearVoucherMap();
        var map = voucherMap.getAllVouchers();
        assertThat(map.isEmpty(), is(true));
    }

    @Test
    @DisplayName("생성된 바우처를 아이디로 제거 시 성공한다.")
    void removeVoucherById_ParamExistVoucher_RemoveVoucher() {
        var voucherId = UUID.randomUUID();
        var voucher = new Voucher(voucherId, VoucherType.FIXED_AMOUNT, new DiscountValue(VoucherType.FIXED_AMOUNT, 10), UUID.randomUUID());
        voucherMap.addVoucher(voucher);
        voucherMap.removeVoucherById(voucherId);
        var maybeNull = voucherMap.getVoucherById(voucherId);
        assertThat(maybeNull, nullValue());
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 바우처 넣으면 바우처 추가 실패한다.")
    @MethodSource("provideValid")
    void addIfVoucherExist_ParamNotExistVoucher_Exception(Voucher voucher) {
        assertThrows(InvalidDataException.class, () -> voucherMap.addIfVoucherExist(voucher));
    }

    @ParameterizedTest
    @DisplayName("고객 아이디로 바우처 검색한다.")
    @MethodSource("provideValid")
    void getAllVouchersByCustomerId_ParamVoucher_ReturnVoucherList(Voucher voucher) {
        voucherMap.addVoucher(voucher);
        var list = voucherMap.getAllVouchersByCustomerId(voucher.getCustomerId());
        assertThat(list.isEmpty(), is(false));
    }

    @ParameterizedTest
    @DisplayName("고객 아이디와 바우처 아이디로 바우처를 찾는다.")
    @MethodSource("provideValid")
    void getVoucherByCustomerIdAndVoucherId_ParamIds_ReturnVoucher(Voucher voucher) {
        voucherMap.addVoucher(voucher);
        var foundVoucher = voucherMap.getVoucherByCustomerIdAndVoucherId(voucher.getCustomerId(), voucher.getVoucherId());
        assertThat(foundVoucher.isEmpty(), is(false));
        assertThat(foundVoucher.get(), samePropertyValuesAs(voucher));
    }

    @ParameterizedTest
    @DisplayName("고객 아이디와 바우처 아이디로 바우처를 제거한다.")
    @MethodSource("provideValid")
    void removeVoucherByCustomerIdAndVoucherId_ParamIds_DeleteVoucher(Voucher voucher) {
        voucherMap.removeVoucherByCustomerIdAndVoucherId(voucher.getCustomerId(), voucher.getVoucherId());
        var vouchers = voucherMap.getVoucherByCustomerIdAndVoucherId(voucher.getCustomerId(), voucher.getVoucherId());
        assertThat(vouchers.isEmpty(), is(true));
    }

}