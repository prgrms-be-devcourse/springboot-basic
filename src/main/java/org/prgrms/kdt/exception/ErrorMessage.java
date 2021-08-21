package org.prgrms.kdt.exception;

public enum ErrorMessage {
    NOT_CORRECT_INPUT_MESSAGE("Not The Correct Message"),
    NOT_FOUND_VOUCHER_MESSAGE("Could Not Find Your Voucher"),
    ERROR_OCCURRED_INPUTTING_FILE("An error occurred while inputting a file"),
    ERROR_READING_FILE("There is a problem reading the file"),
    NAME("이름 형식이 맞지 않습니다."),
    EMAIL("이메일 형식이 맞지 않습니다."),
    PHONE_NUMBER("전화번호 형식이 맞지 않습니다.");

    ErrorMessage(String message) {
    }
}
