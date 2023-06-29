package com.programmers.springbootbasic.service;

import com.programmers.springbootbasic.domain.voucher.Voucher;
import com.programmers.springbootbasic.domain.voucher.VoucherRepository;
import com.programmers.springbootbasic.domain.voucher.VoucherType;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VoucherService {
    VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public void create(String type, String name, Long minimumPriceCondition, LocalDateTime createdDate, LocalDateTime expirationDate, int discount) {
        VoucherType voucherType = VoucherType.from(type);
        Voucher createdVoucher = VoucherFactory.from(voucherType, name, minimumPriceCondition, createdDate, expirationDate, discount);
        voucherRepository.save(createdVoucher);
    }

    public List<Voucher> list() {
        return voucherRepository.findAll();
    }
}
