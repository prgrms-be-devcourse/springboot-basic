package com.prgmrs.voucher.database;

import com.prgmrs.voucher.model.Voucher;

import java.util.List;

public interface VoucherDatabase {
    List<Voucher> load(String filepath);

    void store(Voucher voucher, String filepath);
}
