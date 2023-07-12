package com.programmers.voucher.repository;

import com.programmers.voucher.domain.Voucher;

import java.util.List;
import java.util.UUID;

public interface VoucherRepository {

    Voucher save(Voucher voucher);

    List<Voucher> findAll();

    Voucher findById(UUID voucherId);
}
