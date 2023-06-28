package com.devcourse.springbootbasic.application.domain.voucher.repository;

import com.devcourse.springbootbasic.application.domain.voucher.data.Voucher;

import java.util.List;
import java.util.Optional;

public interface VoucherRepository {
    Optional<Voucher> insert(Voucher voucher);

    List<String> findAll();
}
