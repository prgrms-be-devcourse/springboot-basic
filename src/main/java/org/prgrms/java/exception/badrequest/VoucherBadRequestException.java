package org.prgrms.java.exception.badrequest;

public class VoucherBadRequestException extends BadRequestException {
    public VoucherBadRequestException() {
        super("바우처에 대한 요청이 올바르지 않습니다.");
    }

    public VoucherBadRequestException(String message) {
        super(message);
    }
}
