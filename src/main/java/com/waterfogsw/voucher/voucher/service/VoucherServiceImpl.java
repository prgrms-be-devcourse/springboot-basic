package com.waterfogsw.voucher.voucher.service;

import com.waterfogsw.voucher.voucher.repository.VoucherRepository;
import com.waterfogsw.voucher.voucher.domain.Voucher;
import com.waterfogsw.voucher.voucher.domain.VoucherType;

import java.util.List;

public class VoucherServiceImpl implements VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherServiceImpl(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @Override
    public Voucher createVoucher(VoucherType type, Double value) {
        return null;
    }

    @Override
    public List<Voucher> findAll() {
        return voucherRepository.findAll();
    }
}
