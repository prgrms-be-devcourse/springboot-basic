package org.programmers.VoucherManagement.io;

public interface Input {
    int readType();

    String readDiscountType();

    String readMemberId();

    int readDiscountValue();

    String readMemberStatus();

    String readMemberName();

    String readVoucherId();
}
