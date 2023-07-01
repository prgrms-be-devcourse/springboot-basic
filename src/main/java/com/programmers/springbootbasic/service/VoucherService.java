package com.programmers.springbootbasic.service;

import com.programmers.springbootbasic.domain.voucher.Voucher;
import com.programmers.springbootbasic.domain.voucher.VoucherRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public void create(String voucherType, String name, Long minimumPriceCondition, LocalDateTime createdDate, LocalDateTime expirationDate, int discount) {
        Voucher createdVoucher = VoucherFactory.of(voucherType, name, minimumPriceCondition, createdDate, expirationDate, discount);
        voucherRepository.save(createdVoucher);
    }

    public List<Voucher> list() {
        return voucherRepository.findAll();
    }
}
