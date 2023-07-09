package org.programmers.VoucherManagement.io;

import org.programmers.VoucherManagement.voucher.domain.DiscountType;

public interface Input {
    int readType();

    String readDiscountType();

    String readMemberId();

    int readDiscountValue(DiscountType discountType);

    String readMemberStatus();

    String readMemberName();
}
