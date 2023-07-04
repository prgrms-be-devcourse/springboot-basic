package com.devcourse.springbootbasic.application.repository.voucher;

import com.devcourse.springbootbasic.application.domain.voucher.Voucher;

import java.util.List;

public interface VoucherRepository {
    Voucher insert(Voucher voucher);

    List<Voucher> findAll();
}
