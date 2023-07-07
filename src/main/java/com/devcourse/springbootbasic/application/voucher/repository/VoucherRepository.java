package com.devcourse.springbootbasic.application.voucher.repository;

import com.devcourse.springbootbasic.application.voucher.model.Voucher;

import java.util.List;

public interface VoucherRepository {
    Voucher insert(Voucher voucher);

    List<Voucher> findAllVouchers();
}
