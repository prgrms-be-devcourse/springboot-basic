package org.prgrms.kdt;

public class InputException extends Exception{
    private final String inputException = "입력 형식이 잘못되었습니다.";

    @Override
    public String getMessage(){
        return inputException;
    }
}
