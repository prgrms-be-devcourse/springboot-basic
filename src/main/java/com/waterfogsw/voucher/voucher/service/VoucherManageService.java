package com.waterfogsw.voucher.voucher.service;

import com.waterfogsw.voucher.voucher.domain.Voucher;
import com.waterfogsw.voucher.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

@Service
public class VoucherManageService implements VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherManageService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @Override
    public Voucher addVoucher(Voucher voucher) throws IllegalArgumentException {
        if (voucher == null) throw new IllegalArgumentException();
        return voucherRepository.save(voucher);
    }
}
