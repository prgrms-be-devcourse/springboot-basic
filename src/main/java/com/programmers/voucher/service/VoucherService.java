package com.programmers.voucher.service;

import com.programmers.voucher.domain.Voucher;
import com.programmers.voucher.enumtype.VoucherType;
import com.programmers.voucher.repository.VoucherRepository;
import com.programmers.voucher.request.VoucherCreateRequest;

import java.util.List;
import java.util.UUID;

public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public UUID createVoucher(VoucherCreateRequest request) {
        VoucherType voucherType = request.getVoucherType();

        UUID voucherId = UUID.randomUUID();
        Voucher voucher = voucherType.createVoucher(voucherId, request);

        voucherRepository.save(voucher);

        return voucherId;
    }

    public List<Voucher> findVouchers() {
        return voucherRepository.findAll();
    }

}
