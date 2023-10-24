package org.programmers.springboot.basic.domain.voucher.repository;

import org.programmers.springboot.basic.domain.voucher.entity.Voucher;

import java.util.List;

public interface VoucherRepository {

    void save(Voucher voucher);
    List<Voucher> findAll();
}
