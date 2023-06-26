package com.prgmrs.voucher.service;

import com.prgmrs.voucher.domain.FixedAmountVoucher;
import com.prgmrs.voucher.domain.PercentDiscountVoucher;
import com.prgmrs.voucher.domain.Voucher;
import com.prgmrs.voucher.repository.FixedAmountVoucherRepository;
import com.prgmrs.voucher.repository.PercentDiscountVoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VoucherService {
    private FixedAmountVoucherRepository fixedAmountvoucherRepository;
    private PercentDiscountVoucherRepository percentDiscountVoucherRepository;

    @Autowired
    public VoucherService(FixedAmountVoucherRepository fixedAmountvoucherRepository, PercentDiscountVoucherRepository percentDiscountVoucherRepository) {
        this.fixedAmountvoucherRepository = fixedAmountvoucherRepository;
        this.percentDiscountVoucherRepository = percentDiscountVoucherRepository;
    }

    public Voucher createFixedAmountVoucher(long value) {
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), value);
        return fixedAmountvoucherRepository.save(voucher);

    }

    public Voucher createPercentDiscountVoucher(long value) {
        Voucher voucher = new PercentDiscountVoucher(UUID.randomUUID(), value);
        return percentDiscountVoucherRepository.save(voucher);
    }

}
