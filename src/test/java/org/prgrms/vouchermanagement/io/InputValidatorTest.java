package org.prgrms.vouchermanagement.io;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.prgrms.vouchermanagement.exception.validation.AbnormalCustomerValueException;
import org.prgrms.vouchermanagement.exception.validation.NotNumberException;
import org.prgrms.vouchermanagement.exception.voucher.AbnormalUUIDFormatException;
import org.prgrms.vouchermanagement.exception.voucher.InCorrectDiscountAmountException;
import org.prgrms.vouchermanagement.exception.voucher.InCorrectVoucherTypeException;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InputValidatorTest {

    InputValidator inputValidator = new InputValidator();

    private static Stream<Arguments> correctFixedAmountVoucherDiscountAmountParameters() {
        String fixedAmountVoucherNumberInput = "1";
        return Stream.of(
                Arguments.of(fixedAmountVoucherNumberInput, 0),
                Arguments.of(fixedAmountVoucherNumberInput, 50),
                Arguments.of(fixedAmountVoucherNumberInput, 1000000)
        );
    }

    private static Stream<Arguments> correctPercentAmountVoucherDiscountAmountParameters() {
        String percentAmountVoucherNumberInput = "2";
        return Stream.of(
                Arguments.of(percentAmountVoucherNumberInput, 0),
                Arguments.of(percentAmountVoucherNumberInput, 50),
                Arguments.of(percentAmountVoucherNumberInput, 100)
        );
    }

    private static Stream<Arguments> incorrectFixedAmountVoucherDiscountAmountParameters() {
        String fixedAmountVoucherNumberInput = "1";
        return Stream.of(
                Arguments.of(fixedAmountVoucherNumberInput, -1),
                Arguments.of(fixedAmountVoucherNumberInput, 1000001)
        );
    }

    private static Stream<Arguments> incorrectPercentAmountVoucherDiscountAmountParameters() {
        String percentAmountVoucherNumberInput = "2";
        return Stream.of(
                Arguments.of(percentAmountVoucherNumberInput, -1),
                Arguments.of(percentAmountVoucherNumberInput, 101)
        );
    }

    @Test
    @DisplayName("입력값이 숫자로 변환 가능한 문자열인 경우 검증")
    void numberTypeInput() {
        // given
        String numberInput = "10";

        // when, then
        assertDoesNotThrow(() -> inputValidator.validateNumber(numberInput));
    }

    @Test
    @DisplayName("입력값이 숫자로 변환 불가능한 문자열인 경우 검증")
    void notNumberTypeInput() {
        // given
        String stringInput = "str";

        // when
        assertThrows(NotNumberException.class, () -> inputValidator.validateNumber(stringInput));
    }

    @ParameterizedTest
    @MethodSource("correctFixedAmountVoucherDiscountAmountParameters")
    @DisplayName("FixedAmountVoucher의 올바른 할인금액일 경우 검증")
    void correctFixedAmountVoucherDiscountAmount(String fixedAmountVoucherNumberInput, int discountAmount) {

        // when, then
        assertDoesNotThrow(() -> inputValidator.validateDiscountAmount(fixedAmountVoucherNumberInput, discountAmount));
    }

    @ParameterizedTest
    @MethodSource("correctPercentAmountVoucherDiscountAmountParameters")
    @DisplayName("PercentDiscountVoucher의 올바른 할인률일 경우 검증")
    void correctPercentDiscountVoucherDiscountAmount(String percentDiscountVoucherNumberInput, int discountAmount) {
        // when, then
        assertDoesNotThrow(() -> inputValidator.validateDiscountAmount(percentDiscountVoucherNumberInput, discountAmount));
    }

    @ParameterizedTest
    @MethodSource("incorrectFixedAmountVoucherDiscountAmountParameters")
    @DisplayName("FixedAmountVoucher의 올바르지 않은 할인금액일 경우 검증")
    void incorrectFixedAmountVoucherDiscountAmount(String fixedAmountVoucherNumberInput, int discountAmount) {

        assertThrows(InCorrectDiscountAmountException.class, () -> inputValidator.validateDiscountAmount(fixedAmountVoucherNumberInput, discountAmount));
    }

    @ParameterizedTest
    @MethodSource("incorrectPercentAmountVoucherDiscountAmountParameters")
    @DisplayName("PercentAmountVoucher의 올바르지 않은 할인금액일 경우 검증")
    void incorrectPercentAmountVoucherDiscountAmount(String percentAmountVoucherNumberInput, int discountAmount) {
        assertThrows(InCorrectDiscountAmountException.class, () -> inputValidator.validateDiscountAmount(percentAmountVoucherNumberInput, discountAmount));
    }

    @Test
    @DisplayName("올바르지 않은 사용자 바우처 타입 입력값이 들어왔을 경우 검증")
    void incorrectVoucherTypeNumberInput() {
        // given
        String incorrectVoucherTypeNumberInput = "0";

        // when, then
        assertThrows(InCorrectVoucherTypeException.class, () -> inputValidator.validateVoucherType(incorrectVoucherTypeNumberInput));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Kim", "white space", "한글테스트", "thirtythirtythirtythirtythirty"})
    @DisplayName("올바른 사용자 이름이 들어왔을 경우 검증")
    void correctCustomerNameInput(String correctCustomerNameInput) {
        assertDoesNotThrow(() -> inputValidator.validateName(correctCustomerNameInput));
    }

    @ParameterizedTest
    @ValueSource(strings = {"123", "!@#$%^", "strings length over thirty thir"})
    @DisplayName("올바르지 않은 사용자 이름이 들어왔을 경우 검증")
    void incorrectCustomerNameInput(String incorrectCustomerNameInput) {
        assertThrows(AbnormalCustomerValueException.class, () -> inputValidator.validateName(incorrectCustomerNameInput));
    }

    @ParameterizedTest
    @ValueSource(strings = {"customer123@google.com", "kim_151@naver.com"})
    @DisplayName("올바른 사용자 이메일이 들어왔을 경우 검증")
    void correctCustomerEmailInput(String correctCustomerEmailInput) {
        assertDoesNotThrow(() -> inputValidator.validateEmail(correctCustomerEmailInput));
    }

    @ParameterizedTest
    @ValueSource(strings = {"@naver.com", "kim151", "kong@", "kong@naver.", "kong@.com"})
    @DisplayName("올바르지 않은 사용자 이메일이 들어왔을 경우 검증")
    void incorrectCustomerEmailInput(String incorrectCustomerEmailInput) {
        assertThrows(AbnormalCustomerValueException.class, () -> inputValidator.validateEmail(incorrectCustomerEmailInput));
    }

    @ParameterizedTest
    @ValueSource(strings = {"ea77b434-688f-11ed-9022-0242ac120002", "4860b460-6890-11ed-9022-0242ac120002", "4860b460-6890-11ed-9022-0242ac120002"})
    @DisplayName("올바른 UUID일 경우 검증")
    void correctUUID(String uuid) {
        assertDoesNotThrow(() -> inputValidator.validateUUID(uuid));
    }

    @ParameterizedTest
    @ValueSource(strings = {" ", "12345", "4860b460689011ed90220242ac120002", "4860b460-6890-11ed-9022-0242ac12000", "4860b46-6890-11ed-9022-0242ac120002"})
    @DisplayName("올바르지 않은 UUID일 경우 검증")
    void incorrectUUID(String uuid) {
        assertThrows(AbnormalUUIDFormatException.class, () -> inputValidator.validateUUID(uuid));
    }
}