package com.waterfogsw.voucher.voucher.service;

import com.waterfogsw.voucher.voucher.domain.Voucher;
import com.waterfogsw.voucher.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Override
    public Optional<Voucher> findById(Long voucherId) {
        if (voucherId == null) {
            throw new IllegalArgumentException();
        }
        return voucherRepository.findById(voucherId);
    }
}
