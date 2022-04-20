package org.prgrms.kdt.domain.customer.exception;

import org.prgrms.kdt.domain.common.exception.ExceptionType;
import org.prgrms.kdt.domain.common.exception.NotUpdatedException;

public class CustomerNotUpdatedException extends NotUpdatedException {

    public CustomerNotUpdatedException(ExceptionType errorType) {
        super(errorType);
    }
}
