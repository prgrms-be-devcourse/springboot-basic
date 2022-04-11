package org.prgms.kdtspringvoucher.io;

import org.prgms.kdtspringvoucher.CommandType;
import org.prgms.kdtspringvoucher.voucher.domain.VoucherType;

public interface Input {
    CommandType inputCommandType();
    VoucherType inputVoucherType();
    Long inputDiscountAmountOrPercent();
}
