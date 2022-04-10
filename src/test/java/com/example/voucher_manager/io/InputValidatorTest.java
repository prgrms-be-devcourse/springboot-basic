package com.example.voucher_manager.io;

import com.example.voucher_manager.domain.voucher.VoucherType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class InputValidatorTest {
    private static final InputValidator inputValidator = new InputValidator();

    @ParameterizedTest
    @ValueSource(strings = {"createeee", "1", "!@#!@#!@#"})
    @DisplayName("잘못된 메뉴를 입력하면 ERROR를 반환한다")
    public void invalidMenu(String menu) {
        assertThat(inputValidator.validateCommandType(menu)).isEqualTo(CommandType.ERROR);
    }

    @ParameterizedTest
    @MethodSource("generateValidMenu")
    @DisplayName("올바른 메뉴를 입력하면 입력에 해당하는 커맨드 타입 상수를 반환한다.")
    public void validMenu(String menu, CommandType commandType) {
        assertThat(inputValidator.validateCommandType(menu)).isEqualTo(commandType);
    }

    static Stream<Arguments> generateValidMenu() {
        return Stream.of(
                Arguments.of("create", CommandType.CREATE),
                Arguments.of("list", CommandType.LIST),
                Arguments.of("exit", CommandType.EXIT)
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"fixxxx", "1", "$%%$%"})
    @DisplayName("잘못된 바우처 타입을 선택하면 ERROR를 반환한다")
    public void invalidVoucherType(String input) {
        assertThat(inputValidator.validateVoucherType(input)).isEqualTo(VoucherType.ERROR);
    }

    @ParameterizedTest
    @MethodSource("generateValidVoucherType")
    @DisplayName("올바른 바우처 타입을 입력하면 입력에 해당하는 바우처 타입을 반환한다.")
    public void validMenu(String menu, VoucherType voucherType) {
        assertThat(inputValidator.validateVoucherType(menu)).isEqualTo(voucherType);
    }

    static Stream<Arguments> generateValidVoucherType() {
        return Stream.of(
                Arguments.of("fixed", VoucherType.FIXED),
                Arguments.of("percent", VoucherType.PERCENT)
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"0", "-143", "34"})
    @DisplayName("입력값이 정수일 경우 true를 반환한다")
    public void isInteger(String input) {
        assertThat(inputValidator.isInteger(input)).isEqualTo(true);
    }

    @ParameterizedTest
    @ValueSource(strings = {"!@#!@0", "asdf"})
    @DisplayName("입력값이 정수가 아닐 경우 false를 반환한다")
    public void isNotInteger(String input) {
        assertThat(inputValidator.isInteger(input)).isEqualTo(false);
    }

    @ParameterizedTest
    @ValueSource(longs = {10L, 534324L})
    @DisplayName("유효한 할인 금액(양수)일 경우 true를 반환한다")
    public void isPositiveNumber(Long amount) {
        assertThat(inputValidator.isPositiveNumber(amount)).isEqualTo(true);
    }

    @ParameterizedTest
    @ValueSource(longs = {-10L, 0})
    @DisplayName("유효한 할인 금액(양수)이 아닐 경우 false를 반환한다")
    public void isNotPositiveNumber(Long amount) {
        assertThat(inputValidator.isPositiveNumber(amount)).isEqualTo(false);
    }

    @ParameterizedTest
    @ValueSource(longs = {1L, 100L})
    @DisplayName("유효한 %의 범위(1~100)일 경우 true를 반환한다")
    public void isCorrectRangeOfPercent(Long percent) {
        assertThat(inputValidator.isCorrectRangeOfPercent(percent)).isEqualTo(true);
    }

    @ParameterizedTest
    @ValueSource(longs = {101L, 0L, -50L})
    @DisplayName("유효한 %의 범위(1~100)가 아닐 경우 false를 반환한다")
    public void isNotCorrectRangeOfPercent(Long percent) {
        assertThat(inputValidator.isCorrectRangeOfPercent(percent)).isEqualTo(false);
    }
}
