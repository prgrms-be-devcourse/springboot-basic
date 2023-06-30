package org.programmers.VoucherManagement.io;

import org.programmers.VoucherManagement.voucher.domain.DiscountType;

public interface Input {
    CommandType readType();

    DiscountType readDiscountType();

    int readValue();

}
