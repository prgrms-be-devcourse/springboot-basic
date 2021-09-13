package com.prgrms.devkdtorder.exception;

public class VoucherNotFoundException extends NotFoundException {

    public VoucherNotFoundException(String causedBy) {
        super(ErrorType.VOUCHER_NOT_FOUND.getMessage() + " for " + causedBy);
    }
}
