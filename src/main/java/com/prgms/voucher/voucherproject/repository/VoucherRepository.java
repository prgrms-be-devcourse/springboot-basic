package com.prgms.voucher.voucherproject.repository;

import com.prgms.voucher.voucherproject.domain.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    public Optional<Voucher> findById(UUID voucherId);

    void save(Voucher voucher);

    List<Voucher> findAll();

}
