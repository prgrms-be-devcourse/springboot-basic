package com.programmers.commandline.domain.voucher.repository;

import com.programmers.commandline.domain.voucher.entity.Voucher;

import java.util.List;
import java.util.Optional;

public interface VoucherRepository {
    Voucher insert(Voucher voucher);

    Voucher update(Voucher voucher);

    int count();

    List<Voucher> findAll();

    Optional<Voucher> findById(String voucherId);

    void deleteAll();

    void deleteById(String id);
}
