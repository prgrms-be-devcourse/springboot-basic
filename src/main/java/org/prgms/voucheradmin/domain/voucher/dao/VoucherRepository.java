package org.prgms.voucheradmin.domain.voucher.dao;

import java.util.List;

import org.prgms.voucheradmin.domain.voucher.entity.Voucher;

public interface VoucherRepository {
    Voucher create(Voucher voucher);

    List<Voucher> getAll();
}
