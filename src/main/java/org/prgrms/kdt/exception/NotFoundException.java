package org.prgrms.kdt.exception;

public class NotFoundException extends RuntimeException {
    public enum ErrorMessage {
        NOT_FOUND_VOUCHER_MESSAGE("Could Not Find Your Voucher");

        ErrorMessage(String s) {
        }
    }

    public NotFoundException(NotFoundException.ErrorMessage message) {
        super(message.name());
    }
}
