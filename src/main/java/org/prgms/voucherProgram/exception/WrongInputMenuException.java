package org.prgms.voucherProgram.exception;

public class WrongInputMenuException extends Exception {
    public WrongInputMenuException() {
        super("[ERROR] 올바른 메뉴 입력이 아닙니다.");
    }
}
