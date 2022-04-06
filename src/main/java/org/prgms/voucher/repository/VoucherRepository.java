package org.prgms.voucher.repository;

import java.util.List;

import org.prgms.voucher.entity.Voucher;

public interface VoucherRepository {
    Voucher insert(Voucher voucher);

    List<Voucher> findAll();
}
