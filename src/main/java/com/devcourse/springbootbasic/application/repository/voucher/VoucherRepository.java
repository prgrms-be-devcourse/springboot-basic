package com.devcourse.springbootbasic.application.repository.voucher;

import com.devcourse.springbootbasic.application.domain.Voucher;

import java.util.List;
import java.util.Optional;

public interface VoucherRepository {
    Optional<Voucher> insert(Voucher voucher);

    List<String> findAll();
}
