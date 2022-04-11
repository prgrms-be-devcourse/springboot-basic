package com.programmers.springbootbasic.repository;

import com.programmers.springbootbasic.domain.Voucher;

import java.util.List;
import java.util.Optional;

public interface VoucherRepository {

    Voucher insert(Voucher voucher);

    Optional<Voucher> findById(String voucherId);

    List<Voucher> findAll();

}
