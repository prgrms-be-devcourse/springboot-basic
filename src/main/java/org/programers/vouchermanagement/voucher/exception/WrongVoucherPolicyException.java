package org.programers.vouchermanagement.voucher.exception;

public class WrongVoucherPolicyException extends RuntimeException {

    public WrongVoucherPolicyException(String message) {
        super(message);
    }

    public WrongVoucherPolicyException() {
        this("잘못된 바우처 정책입니다.");
    }
}
