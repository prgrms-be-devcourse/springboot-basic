package org.programmers.springbootbasic.domain.voucher.repository;

import org.programmers.springbootbasic.domain.voucher.model.Voucher;

import java.util.List;

public interface VoucherRepository {
    Voucher save(Voucher voucher);

    List<Voucher> findAll();
}
