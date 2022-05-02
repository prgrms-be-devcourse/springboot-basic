package org.prgms.voucherProgram.domain.voucher.exception;

import org.prgms.voucherProgram.global.error.exception.EntityNotFoundException;

public class VoucherIsNotExistsException extends EntityNotFoundException {
    private static final String MESSAGE = "[ERROR] 해당 아이디로 저장된 바우처가 없습니다.";

    public VoucherIsNotExistsException() {
        super(MESSAGE);
    }

    public VoucherIsNotExistsException(String message) {
        super(message);
    }
}
