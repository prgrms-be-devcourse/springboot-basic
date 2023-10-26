package org.prgms.kdtspringweek1.voucher.repository;

import org.prgms.kdtspringweek1.voucher.entity.Voucher;

import java.util.List;

public interface VoucherRepository {
    Voucher save(Voucher voucher);

    List<Voucher> findAllVouchers();
}
