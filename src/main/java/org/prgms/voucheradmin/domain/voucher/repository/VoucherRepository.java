package org.prgms.voucheradmin.domain.voucher.repository;

import java.util.List;

import org.prgms.voucheradmin.domain.voucher.entity.Voucher;

public interface VoucherRepository {
    Voucher save(Voucher voucher);

    List<Voucher> getAll();
}
