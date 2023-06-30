package com.example.springbootbasic.voucher;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public void commandCreate(Voucher voucher) {
        voucherRepository.save(voucher);
    }

    public List<Voucher> commandList() {
        return voucherRepository.findAll();
    }
}
