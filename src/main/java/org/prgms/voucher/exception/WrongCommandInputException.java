package org.prgms.voucher.exception;

public class WrongCommandInputException extends Exception {
    public WrongCommandInputException() {
        super("[ERROR] 올바른 커맨드가 아닙니다.");
    }
}
