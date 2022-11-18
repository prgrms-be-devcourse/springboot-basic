package com.programmers.voucher.repository.voucher;

import com.programmers.voucher.model.voucher.Voucher;
import com.programmers.voucher.model.voucher.VoucherType;

import java.util.List;
import java.util.UUID;

public interface VoucherRepository {
    Voucher save(Voucher voucher, VoucherType voucherType);

    List<Voucher> findAll();

    Voucher findById(UUID voucherId);

    void update(Voucher voucher, VoucherType voucherType);

    void deleteAll();
}
