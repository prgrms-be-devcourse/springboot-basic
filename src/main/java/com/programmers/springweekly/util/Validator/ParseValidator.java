package com.programmers.springweekly.util.Validator;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public final class ParseValidator {

    private static final String INPUT_SPLIT_LENGTH_ERROR = ",(쉼표)를 기준으로 2개의 단어가 나와야하는데 여러개를 입력하셨습니다. 다시 입력해주세요.";

    public static String[] inputParse(String input) {
        return input.split(",");
    }

    public static void validateVoucherUpdateLength(String[] voucherInfo) {
        validateLengthTwo(voucherInfo);
    }

    public static void validateCustomerLength(String[] customerInfo) {
        validateLengthThree(customerInfo);
    }

    private static void validateLengthTwo(String[] inputInfo) {
        if (inputInfo.length != 2) {
            log.warn(INPUT_SPLIT_LENGTH_ERROR);
            throw new IllegalArgumentException(INPUT_SPLIT_LENGTH_ERROR);
        }
    }

    private static void validateLengthThree(String[] inputInfo) {
        if (inputInfo.length != 3) {
            log.warn(INPUT_SPLIT_LENGTH_ERROR);
            throw new IllegalArgumentException(INPUT_SPLIT_LENGTH_ERROR);
        }
    }


}
