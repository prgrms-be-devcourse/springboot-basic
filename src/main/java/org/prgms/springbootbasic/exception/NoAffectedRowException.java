package org.prgms.springbootbasic.exception;


public class NoAffectedRowException extends RuntimeException {
    public NoAffectedRowException(String reason) {
        super(reason);
    }
}
