package org.prgms.voucherProgram.exception;

public class WrongInputVoucherCommandException extends Exception {
    public WrongInputVoucherCommandException() {
        super("[ERROR] 올바른 바우처 커맨드가 아닙니다.");
    }
}
