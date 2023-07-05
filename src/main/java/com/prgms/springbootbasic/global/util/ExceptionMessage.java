package com.prgms.springbootbasic.global.util;

public enum ExceptionMessage {

    UNDER_MINIMUM_AMOUNT("[Error] Fixed voucher have to discount over 0."),
    OUT_OF_RANGE_PERCENT("[Error] Percent voucher have to discount in range of 1 to 100."),
    NO_SUCH_VOUCHER_TYPE("[Error] Can't Find Such Voucher Type."),
    NO_SUCH_MENU("[Error] Can't Find Such Menu."),
    DUPLICATION_KEY("[Error] Already Exist Key."),
    NO_SUCH_FILE("[Error] Can't Find Such File."),
    CANT_WRITE_FILE("[Error] Cant Write File"),
    CANT_READ_FILE("[Error] Can't Read File");

    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
