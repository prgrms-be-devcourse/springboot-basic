package org.prgms.voucherProgram.domain.wallet.exception;

import org.prgms.voucherProgram.global.error.exception.EntityNotFoundException;

public class NotFoundVoucherException extends EntityNotFoundException {
    private static final String MESSAGE = "[ERROR] 고객이 가진 바우처들에서 해당 아이디를 가진 바우처를 찾을 수 없습니다.";

    public NotFoundVoucherException() {
        super(MESSAGE);
    }

    public NotFoundVoucherException(String message) {
        super(message);
    }
}
