package org.prgrms.kdt.kdtspringorder.common.exception;

import org.prgrms.kdt.kdtspringorder.common.enums.ErrorInfo;

import java.util.UUID;

public class CustomerNotFoundException extends BusinessException {
    public CustomerNotFoundException() {
        super(ErrorInfo.CUSTOMER_NOT_FOUND);
    }
}
