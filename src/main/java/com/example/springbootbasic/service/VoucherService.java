package com.example.springbootbasic.service;

import com.example.springbootbasic.domain.voucher.Voucher;
import com.example.springbootbasic.repository.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherService {
    private static final Logger logger = LoggerFactory.getLogger(VoucherService.class);
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher saveVoucher(Voucher voucher) {
        voucherRepository.save(voucher);
        return voucher;
    }

    public List<Voucher> findAllVouchers() {
        return voucherRepository.findAllVouchers();
    }
}
