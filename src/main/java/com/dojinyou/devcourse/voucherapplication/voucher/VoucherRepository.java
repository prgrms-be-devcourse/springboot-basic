package com.dojinyou.devcourse.voucherapplication.voucher;

import com.dojinyou.devcourse.voucherapplication.voucher.domain.Voucher;

import java.util.List;

public interface VoucherRepository {
    Voucher create(Voucher voucher);

    List<Voucher> findAll();
}
