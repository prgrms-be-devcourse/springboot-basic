package org.programmers.kdt.weekly.io;

import org.programmers.kdt.weekly.voucher.model.VoucherType;

public interface Output {
    void startMessage();
    void inputErrorMessage(Enum<InputErrorType> inputErrorType);
    void voucherSelectMessage();
    void voucherDiscountMessage(Enum<VoucherType> voucherTypeEnum);
}
