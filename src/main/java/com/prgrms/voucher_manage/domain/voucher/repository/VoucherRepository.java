package com.prgrms.voucher_manage.domain.voucher.repository;

import com.prgrms.voucher_manage.domain.voucher.entity.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Voucher save(Voucher voucher);

    List<Voucher> findAll();

    Voucher findById(UUID voucherId);

    void update(Voucher voucher);

    void deleteById(UUID voucherId);
}
