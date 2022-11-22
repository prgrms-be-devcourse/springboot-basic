package org.prgrms.kdt.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.prgrms.kdt.voucher.VoucherType;
import org.prgrms.kdt.voucher.domain.FixedAmountVoucher;
import org.prgrms.kdt.exception.NotFoundVoucherTypeException;
import org.prgrms.kdt.voucher.domain.PercentDiscountVoucher;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.samePropertyValuesAs;

class VoucherTypeTest {

    @ParameterizedTest
    @DisplayName("올바른 타입이 주어졌을경우, 아무일 도 일어나지 않는다.")
    @ValueSource(strings = {"1", "2"})
    void checkTypeCorrectTest(String input) {
        VoucherType.checkType(input);
    }

    @Test
    @DisplayName("잘못된 타입은 입력 받을 수 없다.")
    void checkTypeCorrectTest() {
        Assertions.assertThrows(NotFoundVoucherTypeException.class, () -> VoucherType.checkType("3"));
    }

    @Test
    @DisplayName("생성된 Voucher의 타입을 확인한다.")
    void createVoucherCorrectTest() {
        assertThat(VoucherType.createVoucher("1", 1L, 20L), instanceOf(FixedAmountVoucher.class));
        assertThat(VoucherType.createVoucher("2", 2L, 20L), instanceOf(PercentDiscountVoucher.class));
    }

    @Test
    @DisplayName("제시된 번호 외의 숫자는 선택할 수 없다.")
    void createVoucherInCorrectTest() {
        assertThatThrownBy(() -> VoucherType.createVoucher("3", 1L, 20L)).isInstanceOf(NotFoundVoucherTypeException.class);
    }

    @ParameterizedTest
    @DisplayName("File로 읽어온 String값을 통해 일치하는 타입의 Voucher를 생성할 수 있다.")
    @MethodSource("enumAndStringProvider")
    void selectVoucherTypeCorrectTest(VoucherType type, String input) {
        assertThat(VoucherType.selectVoucherTypeFromTypeName(input), is(type));
    }

    static Stream<Arguments> enumAndStringProvider() {
        return Stream.of(
                arguments(VoucherType.FIXED_AMOUNT, "FixedAmountVoucher{}"),
                arguments(VoucherType.PERCENTAGE, "PercentDiscountVoucher{}")
        );
    }

    @ParameterizedTest
    @DisplayName("selectVoucherTypeFromFile()은 잘못된 타입이 올 수 없다.")
    @ValueSource(strings = {"FixedPercentVoucher", "FixedMoneyVoucher", "PercentAmountVoucher"})
    void selectVoucherTypeInCorrectTest(String input) {
        assertThatThrownBy(() -> VoucherType.selectVoucherTypeFromTypeName(input)).isInstanceOf(NotFoundVoucherTypeException.class);
    }

    @Test
    @DisplayName("파일에서 읽어온 String 값으로 해당하는 타입의 Voucher를 만들 수 있다.")
    void createVoucherV2CorrectTest() {
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(1L,  20L);
        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(2L, 50L);

        assertThat(VoucherType.createVoucher(VoucherType.FIXED_AMOUNT, 1L, 20L), samePropertyValuesAs(fixedAmountVoucher));
        assertThat(VoucherType.createVoucher(VoucherType.PERCENTAGE, 2L, 50L), samePropertyValuesAs(percentDiscountVoucher));
    }
}