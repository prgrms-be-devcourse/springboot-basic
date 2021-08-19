package com.programmers.voucher.repository.voucher;

import com.programmers.voucher.entity.voucher.Voucher;

import java.io.IOException;
import java.util.List;

public interface VoucherRepository {
    void loadVouchers();
    void persistVouchers();

    Voucher save(String name, Voucher.type type);

    List<Voucher> listAll();
}
