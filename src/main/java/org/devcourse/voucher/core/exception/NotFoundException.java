package org.devcourse.voucher.core.exception;

import java.util.UUID;

public class NotFoundException extends RuntimeException  {
    public NotFoundException(ErrorType errorType, UUID voucherId) {
        super(errorType.message() + " -> " + voucherId);
    }
}
