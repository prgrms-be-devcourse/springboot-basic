package org.prgrms.kdt.exception;

public class InvalidVoucherTypeException extends RuntimeException {
    public InvalidVoucherTypeException(){
        super("존재하지 않는 바우처 타입입니다");
    }
}