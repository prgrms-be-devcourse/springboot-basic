package com.devcourse.voucherapp.service;

import com.devcourse.voucherapp.entity.VoucherType;
import com.devcourse.voucherapp.entity.voucher.Voucher;
import com.devcourse.voucherapp.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    @Autowired
    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher create(VoucherType voucherType, String discountAmount) {
        Voucher voucher = voucherType.makeVoucher(discountAmount);

        return voucherRepository.save(voucher);
    }
}
