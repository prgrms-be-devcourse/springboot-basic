package org.programmers.VoucherManagement.member.exception;

public class MemberException extends RuntimeException {
    private MemberExceptionMessage exceptionMessage;

    public MemberException(MemberExceptionMessage exceptionMessage) {
        super(exceptionMessage.getMessage());
    }
}
