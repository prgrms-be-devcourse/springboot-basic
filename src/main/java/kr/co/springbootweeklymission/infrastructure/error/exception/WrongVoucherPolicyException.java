package kr.co.springbootweeklymission.infrastructure.error.exception;

import kr.co.springbootweeklymission.infrastructure.error.model.ResponseStatus;

public class WrongVoucherPolicyException extends RuntimeException {
    public WrongVoucherPolicyException(ResponseStatus responseStatus) {
        super(responseStatus.getMessage());
    }
}
