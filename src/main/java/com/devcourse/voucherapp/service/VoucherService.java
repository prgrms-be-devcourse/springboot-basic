package com.devcourse.voucherapp.service;

import com.devcourse.voucherapp.entity.voucher.Voucher;
import com.devcourse.voucherapp.entity.VoucherType;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {

    public Voucher create(VoucherType voucherType, String discountAmount) {
        Voucher voucher = voucherType.makeVoucher(discountAmount);

        return voucher;
    }
}
