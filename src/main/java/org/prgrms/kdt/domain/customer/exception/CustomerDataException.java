package org.prgrms.kdt.domain.customer.exception;

import org.prgrms.kdt.domain.common.exception.ExceptionType;
import org.prgrms.kdt.domain.common.exception.NotUpdatedException;

public class CustomerDataException extends NotUpdatedException {

    public CustomerDataException(ExceptionType errorType) {
        super(errorType);
    }
}
