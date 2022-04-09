package com.waterfogsw.voucher.voucher;

import java.util.List;

public interface VoucherRepository {
    void save();

    List<Voucher> findAll();
}
