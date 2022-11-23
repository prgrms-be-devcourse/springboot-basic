package org.prgrms.springbootbasic.factory;


import org.prgrms.springbootbasic.entity.voucher.Voucher;

public interface VoucherFactory {

    Voucher createVoucher(long quantity);
}
