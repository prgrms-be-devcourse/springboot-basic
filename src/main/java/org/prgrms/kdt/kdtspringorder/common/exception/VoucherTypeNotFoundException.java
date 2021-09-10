package org.prgrms.kdt.kdtspringorder.common.exception;

import org.prgrms.kdt.kdtspringorder.common.enums.ErrorInfo;

public class VoucherTypeNotFoundException extends BusinessException {
    public VoucherTypeNotFoundException() {
        super(ErrorInfo.VOUCHER_TYPE_NOT_FOUND);
    }
}
