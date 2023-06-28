package org.prgms.voucher.voucher.repository;

import org.prgms.voucher.voucher.Voucher;

import java.util.List;

public interface VoucherRepository {
    Voucher save(Voucher voucher);

    List<Voucher> findAll();
}
