package org.prgrms.kdt.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.prgrms.kdt.domain.FixedAmountVoucher;
import org.prgrms.kdt.domain.PercentDiscountVoucher;
import org.prgrms.kdt.exception.NotFoundVoucherTypeException;

import java.util.UUID;
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
        assertThat(VoucherType.createVoucher("1", UUID.randomUUID(), 20), instanceOf(FixedAmountVoucher.class));
        assertThat(VoucherType.createVoucher("2", UUID.randomUUID(), 20), instanceOf(PercentDiscountVoucher.class));
    }

    @Test
    @DisplayName("제시된 번호 외의 숫자는 선택할 수 없다.")
    void createVoucherInCorrectTest() {
        assertThatThrownBy(() -> VoucherType.createVoucher("3", UUID.randomUUID(), 20)).isInstanceOf(NotFoundVoucherTypeException.class);
    }

    @ParameterizedTest
    @DisplayName("File로 읽어온 String값을 통해 일치하는 타입의 Voucher를 생성할 수 있다.")
    @MethodSource("enumAndStringProvider")
    void selectVoucherTypeCorrectTest(VoucherType type, String input) {
        assertThat(VoucherType.selectVoucherTypeFromFile(input), is(type));
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
        assertThatThrownBy(() -> VoucherType.selectVoucherTypeFromFile(input)).isInstanceOf(NotFoundVoucherTypeException.class);
    }

    @Test
    @DisplayName("createVoucherFromFile() 정상동작 테스트")
    void createVoucherFromFileCorrectTest() {
        UUID fixedUUID = UUID.randomUUID();
        UUID percentUUID = UUID.randomUUID();
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(fixedUUID, 20);
        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(percentUUID, 50);

        assertThat(VoucherType.createVoucherFromFile(VoucherType.FIXED_AMOUNT, "voucherId=" + fixedUUID, "amount=20"), samePropertyValuesAs(fixedAmountVoucher));
        assertThat(VoucherType.createVoucherFromFile(VoucherType.PERCENTAGE, "voucherId=" + percentUUID, "percent=50"), samePropertyValuesAs(percentDiscountVoucher));
    }
}