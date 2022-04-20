package org.programmer.kdtspringboot.voucher;

import java.util.List;

public interface VoucherRepository {
    void saveVoucher(Voucher voucher);
    List<Voucher> findAll();
}
