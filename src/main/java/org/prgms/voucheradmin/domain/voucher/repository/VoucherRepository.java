package org.prgms.voucheradmin.domain.voucher.repository;

import java.util.List;

import org.prgms.voucheradmin.domain.voucher.entity.Voucher;
import org.springframework.stereotype.Repository;

public interface VoucherRepository {
    Voucher create(Voucher voucher);

    List<Voucher> getAll();
}
