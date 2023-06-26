package com.demo.voucher.service;

import com.demo.voucher.domain.Voucher;
import com.demo.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class VoucherServiceImpl implements VoucherService {
    private static final String NOT_FOUND_VOUCHER = "바우처를 찾을 수 없습니다.";
    private final VoucherRepository voucherRepository;


    public VoucherServiceImpl(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @Override
    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new RuntimeException(NOT_FOUND_VOUCHER));
    }

    @Override
    public Map<UUID, Voucher> findAllVouchers() {
        return voucherRepository.findAll();
    }

    @Override
    public Voucher createVoucher(Voucher voucher) {
        return voucherRepository.insert(voucher);
    }
}
