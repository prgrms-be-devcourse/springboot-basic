package com.programmers.voucher.service;

import com.programmers.voucher.domain.Voucher;
import com.programmers.voucher.domain.VoucherType;
import com.programmers.voucher.repository.VoucherRepository;
import com.programmers.voucher.request.VoucherCreationRequest;

import java.util.List;

public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public void createVoucher(VoucherCreationRequest voucherCreationRequest) {
        VoucherType voucherType = VoucherType.getVoucherType(voucherCreationRequest.getType());
        Voucher voucher = voucherType.createVoucher(voucherCreationRequest);
        voucherRepository.save(voucher);
    }

    public List<Voucher> findVoucherList() {
        return voucherRepository.findAll();
    }
}
