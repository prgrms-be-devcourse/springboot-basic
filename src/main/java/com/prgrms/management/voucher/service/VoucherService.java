package com.prgrms.management.voucher.service;

import com.prgrms.management.voucher.domain.Voucher;
import com.prgrms.management.voucher.domain.VoucherType;
import com.prgrms.management.voucher.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    @Autowired
    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher createVoucher(String inputVoucherType, String inputAmount) {
        Voucher voucher = VoucherType.of(inputVoucherType).createVoucher(inputAmount);
        return voucherRepository.insert(voucher);
    }

    public List<Voucher> findAll() {
        return voucherRepository.findAll();
    }
}
