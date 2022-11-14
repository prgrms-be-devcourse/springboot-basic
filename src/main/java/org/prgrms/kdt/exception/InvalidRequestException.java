package org.prgrms.kdt.exception;

public class InvalidRequestException extends RuntimeException {
    public InvalidRequestException(){
        super("양식에 맞지 않는 요청입니다");
    }
}