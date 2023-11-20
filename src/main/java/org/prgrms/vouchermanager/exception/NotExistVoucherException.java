package org.prgrms.vouchermanager.exception;

public class NotExistVoucherException extends RuntimeException{
    public NotExistVoucherException() {
        super("해당 바우처를 가진 고객은 존재하지 않습니다. 다시 입력해주세요");
    }
}
