package org.prgrms.kdt.database;

import org.prgrms.kdt.voucher.Voucher;

import java.util.List;

public interface VoucherDatabase {
    Voucher saveVoucher(Voucher newVoucher);

    List<Voucher> findAllVoucher();
}
