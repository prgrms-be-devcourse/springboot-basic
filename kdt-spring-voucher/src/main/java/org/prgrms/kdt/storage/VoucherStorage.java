package org.prgrms.kdt.storage;

import org.prgrms.kdt.voucher.Voucher;

import java.util.List;

public interface VoucherStorage {
    void saveVoucher(Voucher newVoucher);

    List<Voucher> findAllVoucher();
}
