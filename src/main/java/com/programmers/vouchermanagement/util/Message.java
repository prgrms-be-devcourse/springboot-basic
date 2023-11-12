package com.programmers.vouchermanagement.util;

public class Message {
    public static final String NOT_INSERTED = "Noting was inserted!";
    public static final String NOT_UPDATED = "Noting was updated!";
    public static final String NOT_DELETED = "Noting was deleted!";
    public static final String EMPTY_RESULT = "Got empty result!";
    public static final String IO_EXCEPTION = "Error raised while reading file!";
    public static final String NOT_FOUND_VOUCHER = "There is no such voucher.";
    public static final String NOT_FOUND_CUSTOMER = "There is no such customer.";
    public static final String NOT_FOUND_VOUCHER_ALLOCATION = "There is no voucher allocation information.";
    public static final String NOT_FOUND_CUSTOMER_ALLOCATION = "There is no customer allocation information.";
    public static final String CAN_NOT_INSERT_OWNERSHIP = "There is no customer allocation information.";
    public static final String ALREADY_EMPTY_TABLE = "The table is already empty.";
    public static final String FILE_EXCEPTION = "Error raised while opening the file.";
    public static final String INVALID_VOUCHER_TYPE = "Voucher type should be either fixed amount or percent discount voucher.";

    private Message() {
    }
}
