package org.prgrms.kdt.kdtspringorder.common.exception;

import org.prgrms.kdt.kdtspringorder.common.enums.ErrorMessage;

import java.text.MessageFormat;
import java.util.UUID;

public class VoucherNotFoundException extends BusinessException{

    public VoucherNotFoundException(UUID voucherId) {
        super(ErrorMessage.VOUCHER_NOT_FOUND_MESSAGE.getMessage() + " ( voucherId = " + voucherId + " )");
    }

}
