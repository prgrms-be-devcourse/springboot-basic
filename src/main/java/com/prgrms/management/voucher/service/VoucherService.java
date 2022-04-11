package com.prgrms.management.voucher.service;

import com.prgrms.management.voucher.domain.FixedAmountVoucher;
import com.prgrms.management.voucher.domain.PercentAmountVoucher;
import com.prgrms.management.voucher.domain.Voucher;
import com.prgrms.management.voucher.domain.VoucherType;
import com.prgrms.management.voucher.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    @Autowired
    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher createVoucher(VoucherType voucherType, long amount) {
        Voucher voucher = voucherType.createVoucher(amount);
        return voucherRepository.insert(voucher);
    }

    public String findAll() {
        List<Voucher> vouchers = voucherRepository.findAll().orElse(null);
        return (vouchers.isEmpty()) ? "": vouchers.toString();
    }
}
