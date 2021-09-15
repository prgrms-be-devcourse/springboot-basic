package org.prgrms.kdt.exception;

public enum ErrorMessage {
    NOT_CORRECT_INPUT_MESSAGE("Not The Correct Message"),
    NOT_FOUND_VOUCHER_MESSAGE("Could Not Find Your Voucher"),
    ERROR_OCCURRED_INPUTTING_FILE("An error occurred while inputting a file"),
    ERROR_READING_FILE("There is a problem reading the file"),
    NAME("The Name format is not correct."),
    EMAIL("The Email format is not correct."),
    PASSWORD("The Password format is not correct."),
    PHONE_NUMBER("The PhoneNumber format is not correct."),
    MORE_THAN_MIN_VOUCHER_AMOUNT("Amount should be more than your Input Data"),
    NOT_BE_ZERO_VOUCHER_AMOUNT("Amount should not be Zero"),
    LESS_THAN_MAX_VOUCHER_AMOUNT("Amount should be less than your Input Data"),
    MORE_THAN_MIN_VOUCHER_PERCENT("Percent should be more than your Input Data"),
    NOT_BE_ZERO_VOUCHER_PERCENT("Percent should not be Zero"),
    LESS_THAN_MAX_VOUCHER_PERCENT("Percent should be less than your Input Data"),
    CAM_NOT_UPDATE_ROLE("Can not update role"),
    CAN_NOT_INSERT("Can not Insert"),
    CAN_NOT_DELETE("Can not Delete");


    ErrorMessage(String message) {
    }
}
