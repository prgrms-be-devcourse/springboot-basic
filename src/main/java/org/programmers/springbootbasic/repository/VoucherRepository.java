package org.programmers.springbootbasic.repository;

import org.programmers.springbootbasic.domain.Voucher;

import java.util.List;

public interface VoucherRepository {
    Voucher save(Voucher voucher);
    List<Voucher> findAll();
}
