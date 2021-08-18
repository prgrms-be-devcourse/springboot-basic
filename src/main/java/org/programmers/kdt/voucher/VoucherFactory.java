package org.programmers.kdt.voucher;

public abstract class VoucherFactory {
    abstract public Voucher createVoucher(long discount);
}
