package org.programmers.springbootbasic.domain;

import org.programmers.springbootbasic.data.VoucherType;

public interface VoucherFactory {
    Voucher getVoucher(VoucherType type, long amount);
}
