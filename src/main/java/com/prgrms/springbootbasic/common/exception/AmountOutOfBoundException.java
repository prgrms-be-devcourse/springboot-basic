package com.prgrms.springbootbasic.common.exception;

import java.text.MessageFormat;

public class AmountOutOfBoundException extends IllegalArgumentException {
    private static final String MESSAGE_FORMAT = "Your voucher type: {0}, Voucher amount input {1} out of bound {2} ~ {3}";
    private final ErrorCode errorCode;

//    public AmountOutOfBoundException(String voucherType, int minBoundary, int maxBoundary) {
//        super(MessageFormat.format(MESSAGE_FORMAT, voucherType, minBoundary, maxBoundary));
//    }

    public AmountOutOfBoundException(String voucherType, int discountAmount, int minBoundary, int maxBoundary, ErrorCode errorCode){
        super(MessageFormat.format(MESSAGE_FORMAT, discountAmount, voucherType, minBoundary, maxBoundary));
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode(){
        return errorCode;
    }
}
