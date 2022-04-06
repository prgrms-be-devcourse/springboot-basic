package org.prgms.voucher.repository;

import java.util.List;

import org.prgms.voucher.entity.Voucher;

public interface VoucherRepository {
    Voucher save(Voucher voucher);

    List<Voucher> findAll();
}
