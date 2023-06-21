package com.dev.voucherproject.service;


import com.dev.voucherproject.model.voucher.Voucher;
import com.dev.voucherproject.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public void insert(Voucher voucher) {
        voucherRepository.insert(voucher);
    }

    public List<Voucher> findAll() {
        return voucherRepository.findAll();
    }
}
