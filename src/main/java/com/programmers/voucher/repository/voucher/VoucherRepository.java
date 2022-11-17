package com.programmers.voucher.repository.voucher;

import com.programmers.voucher.model.voucher.Voucher;
import com.programmers.voucher.model.voucher.VoucherType;

import java.util.List;

public interface VoucherRepository {
    Voucher save(Voucher voucher, VoucherType voucherType);

    List<Voucher> findAll();
}
