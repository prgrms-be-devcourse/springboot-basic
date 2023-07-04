package org.programmers.VoucherManagement.io;

import org.programmers.VoucherManagement.voucher.domain.DiscountType;

public interface Input {
    MenuType readType();

    DiscountType readDiscountType();

    int readValue();

}
