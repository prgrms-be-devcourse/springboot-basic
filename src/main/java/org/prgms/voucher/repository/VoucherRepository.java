package org.prgms.voucher.repository;

import org.prgms.voucher.Voucher;

import java.util.List;

public interface VoucherRepository {
    void save(Voucher voucher);

    List<Voucher> findAll();
}
