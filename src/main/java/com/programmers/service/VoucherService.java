package com.programmers.service;

import com.programmers.domain.Voucher;
import com.programmers.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public void save(Voucher voucher) {
        voucherRepository.save(voucher);
    }

    public List<Voucher> findAll() {
        return voucherRepository.findAll();
    }
}
