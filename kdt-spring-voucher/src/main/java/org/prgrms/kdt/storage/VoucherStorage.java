package org.prgrms.kdt.storage;

import org.prgrms.kdt.voucher.Voucher;

import java.util.List;

public interface VoucherStorage {
    Voucher saveVoucher(Voucher newVoucher);

    List<Voucher> findAllVoucher();
}
