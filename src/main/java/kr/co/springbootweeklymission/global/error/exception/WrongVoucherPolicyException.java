package kr.co.springbootweeklymission.global.error.exception;

import kr.co.springbootweeklymission.global.response.ResponseStatus;

public class WrongVoucherPolicyException extends RuntimeException {
    public WrongVoucherPolicyException(ResponseStatus responseStatus) {
        super(responseStatus.getMessage());
    }
}
