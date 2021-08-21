package org.prgrms.kdt.exception;

public enum ErrorMessage {
    NOT_CORRECT_INPUT_MESSAGE("Not The Correct Message"),
    NOT_FOUND_VOUCHER_MESSAGE("Could Not Find Your Voucher"),
    ERROR_OCCURRED_INPUTTING_FILE("An error occurred while inputting a file"),
    ERROR_READING_FILE("There is a problem reading the file");

    ErrorMessage(String s) {
    }
}
