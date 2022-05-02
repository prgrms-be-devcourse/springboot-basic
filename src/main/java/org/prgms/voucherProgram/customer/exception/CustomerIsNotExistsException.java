package org.prgms.voucherProgram.customer.exception;

import org.prgms.voucherProgram.global.error.exception.EntityNotFoundException;

public class CustomerIsNotExistsException extends EntityNotFoundException {
    private static final String MESSAGE = "[ERROR] 해당 이메일로 저장된 고객이 없습니다.";

    public CustomerIsNotExistsException() {
        super(MESSAGE);
    }

    public CustomerIsNotExistsException(String message) {
        super(message);
    }
}
