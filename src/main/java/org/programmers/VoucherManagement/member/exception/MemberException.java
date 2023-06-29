package org.programmers.VoucherManagement.member.exception;

import org.programmers.VoucherManagement.member.domain.Member;

public class MemberException extends RuntimeException {
    private MemberExceptionMessage exceptionMessage;
    public MemberException(MemberExceptionMessage exceptionMessage){
        super(exceptionMessage.getMessage());
    }

    public MemberException(MemberExceptionMessage exceptionMessage,Throwable cause) {
        super(exceptionMessage.getMessage(),cause);
    }
}
