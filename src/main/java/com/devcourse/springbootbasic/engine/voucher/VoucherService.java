package com.devcourse.springbootbasic.engine.voucher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//@Service
public class VoucherService {

    @Autowired
    private VoucherRepository voucherRepository;

    public Voucher createVoucher(Voucher voucher) {
        return voucherRepository.insert(voucher);
    }

    public List<Voucher> getAllVouchers() {
        return voucherRepository.findAll();
    }
}
