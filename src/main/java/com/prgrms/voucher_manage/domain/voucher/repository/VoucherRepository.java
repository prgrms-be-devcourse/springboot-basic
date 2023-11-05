package com.prgrms.voucher_manage.domain.voucher.repository;

import com.prgrms.voucher_manage.domain.voucher.entity.Voucher;
import com.prgrms.voucher_manage.domain.voucher.entity.VoucherType;

import java.util.List;
import java.util.UUID;

public interface VoucherRepository {
    Voucher save(Voucher voucher);

    List<Voucher> getAll();

    List<Voucher> getByType(VoucherType type);

    Voucher getById(UUID voucherId);

    void update(Voucher voucher);

    void deleteById(UUID voucherId);
}
