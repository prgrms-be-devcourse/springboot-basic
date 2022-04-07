package org.prgms.voucher.exception;

public class WrongVoucherNameException extends Exception {
    public WrongVoucherNameException() {
        super("[ERROR] 올바른 바우처 이름이 아닙니다.");
    }
}
