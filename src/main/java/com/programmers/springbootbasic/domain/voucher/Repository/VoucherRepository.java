package com.programmers.springbootbasic.domain.voucher.Repository;

import com.programmers.springbootbasic.domain.voucher.Voucher;

import java.util.List;
import java.util.Optional;

public interface VoucherRepository {
    Optional<Voucher> save(Voucher voucher);

    List<Voucher> findAll();

}
