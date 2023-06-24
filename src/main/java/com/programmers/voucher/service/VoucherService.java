package com.programmers.voucher.service;

import com.programmers.voucher.domain.Voucher;
import com.programmers.voucher.domain.VoucherFactory;
import com.programmers.voucher.repository.VoucherRepository;
import com.programmers.voucher.request.VoucherCreationRequest;

import java.util.List;
import java.util.UUID;

public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public UUID createVoucher(VoucherCreationRequest voucherCreationRequest) {
        Voucher voucher = VoucherFactory.createVoucher(voucherCreationRequest);
        voucherRepository.save(voucher);
        return voucher.getVoucherId();
    }

    public List<Voucher> findVoucherList() {
        return voucherRepository.findAll();
    }
}
