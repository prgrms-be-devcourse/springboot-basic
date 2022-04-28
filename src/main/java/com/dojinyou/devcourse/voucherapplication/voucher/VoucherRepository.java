package com.dojinyou.devcourse.voucherapplication.voucher;

import com.dojinyou.devcourse.voucherapplication.voucher.domain.Voucher;
import com.dojinyou.devcourse.voucherapplication.voucher.domain.VoucherList;

public interface VoucherRepository {
    Voucher create(Voucher voucher);

    VoucherList findAll();
}
