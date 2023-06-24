package com.prgrms.springbootbasic.service;

import com.prgrms.springbootbasic.domain.Voucher;
import com.prgrms.springbootbasic.repository.VoucherRepository;

import java.util.ArrayList;
import java.util.List;

public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher createVoucher(Voucher voucher) {
        return voucherRepository.insert(voucher);
    }
    public List<Voucher> fetchAllVouchers() {
        return new ArrayList<>(voucherRepository.getAllVouchersList());
    }
}
