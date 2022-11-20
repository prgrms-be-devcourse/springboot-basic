package org.prgrms.vouchermanagement.exception.voucher;

public class AbnormalUUIDFormatException extends RuntimeException {
    public AbnormalUUIDFormatException() {
        super("올바르지 않은 형태의 UUID 값입니다.");
    }
}
