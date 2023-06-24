package com.devcourse.voucherapp.service;

import com.devcourse.voucherapp.entity.Voucher;
import com.devcourse.voucherapp.entity.VoucherType;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {

    public void create(VoucherType voucherType, String discountAmount) {
        Voucher voucher = voucherType.makeVoucher(discountAmount);
    }
}
