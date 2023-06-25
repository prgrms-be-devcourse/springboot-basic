package org.prgrms.kdt.exception;

public class DatabaseReadException extends RuntimeException{
    public static final String msg = "데이터 베이스에서 값을 읽어오는 도중 문제가 생겼습니다.";

    public DatabaseReadException(){
        super(msg);
    }
}
