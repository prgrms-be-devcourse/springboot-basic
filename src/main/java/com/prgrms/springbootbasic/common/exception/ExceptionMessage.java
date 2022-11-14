package com.prgrms.springbootbasic.common.exception;

public class ExceptionMessage {

    private ExceptionMessage() {
    }

    public static final String FATAL_FILE_IO_EXCEPTION_MESSAGE = "Fatal error occurred while operating I/O: {}";
    public static final String FILE_NOT_EXIST_EXCEPTION_MESSAGE = "Fatal error occurred while opening file: ";

    public static final String ILLEGAL_STATE_EXCEPTION_WHEN_DISCOUNT = "Discount result is under zero. You cannot use this voucher.";
    public static final String VOUCHER_NOT_SUPPORTED = "Voucher not supported yet.";
}
