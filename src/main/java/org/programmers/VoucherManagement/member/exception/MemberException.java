package org.programmers.VoucherManagement.member.exception;

public class MemberException extends RuntimeException {

    public MemberException(MemberExceptionMessage exceptionMessage) {
        super(exceptionMessage.getMessage());
    }
}
