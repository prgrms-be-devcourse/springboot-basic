package org.prgrms.vouchermanager.exception;

public class InputValueException extends RuntimeException{
    public InputValueException() {
        super("입력값이 잘못 되었습니다. 지정된 범위 내의 사항을 입력해주세요.");
    }
}
