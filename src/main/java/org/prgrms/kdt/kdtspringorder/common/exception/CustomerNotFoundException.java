package org.prgrms.kdt.kdtspringorder.common.exception;

import org.prgrms.kdt.kdtspringorder.common.enums.ErrorMessage;

import java.text.MessageFormat;
import java.util.UUID;

public class CustomerNotFoundException extends BusinessException {
    public CustomerNotFoundException(UUID customerId) {
        super(MessageFormat.format(ErrorMessage.CUSTOMER_NOT_FOUND_MESSAGE.getMessage(), customerId));
    }
}
