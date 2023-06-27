package org.programmers.VoucherManagement.io;

import org.programmers.VoucherManagement.CommandType;
import org.programmers.VoucherManagement.DiscountType;

public interface Input {
    CommandType readType();
    DiscountType readDiscountType();
    int readDiscountValue();
}
