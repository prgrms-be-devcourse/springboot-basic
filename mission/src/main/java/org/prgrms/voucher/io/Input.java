package org.prgrms.voucher.io;

import org.prgrms.voucher.models.VoucherType;

public interface Input {

    String getString();

    VoucherType getVoucherType();

    long getDiscountValue();
}