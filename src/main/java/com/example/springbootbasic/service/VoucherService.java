package com.example.springbootbasic.service;

import com.example.springbootbasic.repository.VoucherRepository;
import com.example.springbootbasic.domain.voucher.Voucher;
import com.example.springbootbasic.domain.voucher.VoucherGeneratorEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    @Autowired
    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Long saveVoucher(String voucherType, Long amount) {
        Long newVoucherId = voucherRepository.getSequence();
        Voucher generatedVoucher = VoucherGeneratorEnum.generateVoucher(newVoucherId, amount, voucherType);
        voucherRepository.save(newVoucherId, generatedVoucher);
        return newVoucherId;
    }
}
