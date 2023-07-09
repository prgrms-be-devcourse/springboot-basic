package org.programmers.VoucherManagement.member.exception;

public class MemberException extends RuntimeException {
    private final MemberExceptionMessage exceptionMessage;

    public MemberException(MemberExceptionMessage exceptionMessage) {
        super(exceptionMessage.getMessage());
        this.exceptionMessage = exceptionMessage;
    }

    public MemberException(MemberExceptionMessage exceptionMessage, Throwable cause) {
        super(exceptionMessage.getMessage(), cause);
        this.exceptionMessage = exceptionMessage;
    }
}
