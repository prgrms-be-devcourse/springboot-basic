package org.programmers.VoucherManagement.member.exception;

import lombok.Getter;
import org.programmers.VoucherManagement.global.response.ErrorCode;

@Getter
public class MemberException extends RuntimeException {
    private final ErrorCode errorCode;

    public MemberException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
