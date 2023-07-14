package com.programmers.springweekly.util.validator;

import java.util.Arrays;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public final class ParseValidator {

    public static String[] inputParse(String input) {
        return input.split(",");
    }

    public static void validateVoucherUpdateLength(String[] voucherInfo) {
        validateLengthTwo(voucherInfo);
    }

    public static void validateCustomerLength(String[] customerInfo) {
        validateLengthThree(customerInfo);
    }

    public static void validateBlacklistFileLineLength(String[] readLine) {
        validateBlacklistLengthFour(readLine);
    }

    public static void validateVoucherFileLineLength(String[] readLine) {
        validateVoucherLengthThree(readLine);
    }

    private static void validateLengthTwo(String[] inputInfo) {
        if (inputInfo.length != 2) {
            log.warn("Input: {}, 쉼포를 기준으로 2개의 단어가 나와야하지만 2개가 나오지 않아서 예외 발생", Arrays.toString(inputInfo));
            throw new IllegalArgumentException(",(쉼표)를 기준으로 2개의 단어가 나와야하는데 2개가 아닙니다. 다시 입력해주세요.");
        }
    }

    private static void validateLengthThree(String[] inputInfo) {
        if (inputInfo.length != 3) {
            log.warn("Input: {}, 쉼포를 기준으로 3개의 단어가 나와야하지만 3개가 나오지 않아서 예외 발생", Arrays.toString(inputInfo));
            throw new IllegalArgumentException(",(쉼표)를 기준으로 3개의 단어가 나와야하는데 3개가 아닙니다. 다시 입력해주세요.");
        }
    }

    private static void validateBlacklistLengthFour(String[] readLine) {
        if (readLine.length != 4) {
            log.warn("Input: {}, 쉼포를 기준으로 4개의 단어가 나와야하지만 4개가 나오지 않아서 예외 발생", Arrays.toString(readLine));
            throw new IndexOutOfBoundsException(",(쉼표)를 기준으로 4개의 단어가 나와야하는데 4개가 아닙니다. 다시 입력해주세요.");
        }
    }

    private static void validateVoucherLengthThree(String[] readLine) {
        if (readLine.length != 3) {
            log.warn("Input: {}, 쉼포를 기준으로 3개의 단어가 나와야하지만 3개가 나오지 않아서 예외 발생", Arrays.toString(readLine));
            throw new IndexOutOfBoundsException(",(쉼표)를 기준으로 3개의 단어가 나와야하는데 3개가 아닙니다. 다시 입력해주세요.");
        }
    }

}

