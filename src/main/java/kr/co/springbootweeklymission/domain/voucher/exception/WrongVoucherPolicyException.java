package kr.co.springbootweeklymission.domain.voucher.exception;

import kr.co.springbootweeklymission.global.error.ResponseStatus;

public class WrongVoucherPolicyException extends RuntimeException {
    public WrongVoucherPolicyException(ResponseStatus responseStatus) {
        super(responseStatus.getMessage());
    }
}
