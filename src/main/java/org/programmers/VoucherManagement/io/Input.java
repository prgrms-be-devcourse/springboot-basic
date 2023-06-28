package org.programmers.VoucherManagement.io;

import org.programmers.VoucherManagement.command.domain.CommandType;
import org.programmers.VoucherManagement.voucher.domain.DiscountType;

public interface Input {
    CommandType readType();

    DiscountType readDiscountType();

    int readFixedDiscountValue();

    int readPercentDiscountValue();
}
