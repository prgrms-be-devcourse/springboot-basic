package com.example.kdtspringmission.voucher.service;

import com.example.kdtspringmission.voucher.domain.FixedAmountVoucher;
import com.example.kdtspringmission.voucher.domain.RateAmountVoucher;
import com.example.kdtspringmission.voucher.domain.Voucher;
import com.example.kdtspringmission.voucher.repository.VoucherRepository;
import java.text.MessageFormat;
import java.util.List;

public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher create(String type) {
        if (type.equals("1")) {
            return new FixedAmountVoucher();
        }

        if (type.equals("2")) {
            return new RateAmountVoucher();
        }
        throw new IllegalArgumentException(MessageFormat.format("No such voucher name = {0}", type));
    }

    public Long createAndPersist(String type) {
        return voucherRepository.insert(this.create(type));
    }

    public List<Voucher> findAll() {
        return voucherRepository.findAll();
    }
}
