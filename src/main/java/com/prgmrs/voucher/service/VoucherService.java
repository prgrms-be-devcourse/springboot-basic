package com.prgmrs.voucher.service;

import com.prgmrs.voucher.domain.FixedAmountVoucher;
import com.prgmrs.voucher.domain.PercentDiscountVoucher;
import com.prgmrs.voucher.domain.Voucher;
import com.prgmrs.voucher.repository.MemoryVoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VoucherService {
    private MemoryVoucherRepository memoryVoucherRepository;
    @Autowired
    public VoucherService(MemoryVoucherRepository memoryVoucherRepository) {
        this.memoryVoucherRepository = memoryVoucherRepository;
    }

}
