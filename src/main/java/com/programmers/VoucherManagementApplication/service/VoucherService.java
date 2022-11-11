package com.programmers.VoucherManagementApplication.service;

import com.programmers.VoucherManagementApplication.repository.VoucherRepository;
import com.programmers.VoucherManagementApplication.voucher.Voucher;
import com.programmers.VoucherManagementApplication.voucher.VoucherType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public void create(Long originPrice, Long amount, VoucherType voucherType) {
        voucherRepository.addVoucher(new Voucher(UUID.randomUUID(), originPrice, amount, voucherType));
    }

    public List<Voucher> findAll() {
        return voucherRepository.findAll();
    }
}
