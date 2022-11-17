package org.prgrms.springbootbasic.factory;

import org.prgrms.springbootbasic.voucher.Voucher;

public interface VoucherFactory {
    Voucher createVoucher(long factor);
}
