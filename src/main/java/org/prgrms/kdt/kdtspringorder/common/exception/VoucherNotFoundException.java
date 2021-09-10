package org.prgrms.kdt.kdtspringorder.common.exception;

import org.prgrms.kdt.kdtspringorder.common.enums.ErrorInfo;

import java.util.UUID;

public class VoucherNotFoundException extends BusinessException{

    public VoucherNotFoundException(UUID voucherId) {
        super(ErrorInfo.VOUCHER_NOT_FOUND);
    }

}
