package com.programmers.kwonjoosung.springbootbasicjoosung.repository.voucher;

import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.Voucher;

import java.util.List;
import java.util.UUID;

public interface VoucherRepository {

    Voucher insert(Voucher voucher);

    Voucher findById(UUID voucherId);

    Voucher update(Voucher voucher);

    int deleteById(UUID voucherId);

    List<Voucher> findAll();

}
