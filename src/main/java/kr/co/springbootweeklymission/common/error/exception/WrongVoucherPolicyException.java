package kr.co.springbootweeklymission.common.error.exception;

import kr.co.springbootweeklymission.common.response.ResponseStatus;

public class WrongVoucherPolicyException extends RuntimeException {
    public WrongVoucherPolicyException(ResponseStatus responseStatus) {
        super(responseStatus.getMessage());
    }
}
