package org.prgms.springbootbasic.exception;

public class OutOfRangeException extends IllegalArgumentException{
    public OutOfRangeException(String s) {
        super(s);
    }
}
