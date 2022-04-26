package com.dojinyou.devcourse.voucherapplication.voucher;

import com.dojinyou.devcourse.voucherapplication.voucher.domain.Voucher;

public interface VoucherRepository {
    Voucher create(Voucher voucher);
}
