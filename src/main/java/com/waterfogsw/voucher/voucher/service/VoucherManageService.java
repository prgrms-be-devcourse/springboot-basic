package com.waterfogsw.voucher.voucher.service;

import com.waterfogsw.voucher.voucher.domain.Voucher;
import com.waterfogsw.voucher.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherManageService implements VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherManageService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @Override
    public Voucher saveVoucher(Voucher voucher) {
        if (voucher == null) {
            throw new IllegalArgumentException();
        }
        return voucherRepository.save(voucher);
    }

    @Override
    public List<Voucher> findAllVoucher() {
        return voucherRepository.findAll();
    }
}
