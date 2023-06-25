package com.prgrms.springbootbasic.service;

import com.prgrms.springbootbasic.domain.FixedDiscountVoucher;
import com.prgrms.springbootbasic.domain.PercentDiscountVoucher;
import com.prgrms.springbootbasic.domain.Voucher;
import com.prgrms.springbootbasic.repository.VoucherRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class VoucherService {
    private final VoucherRepository voucherRepository;
    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher createFixedDiscountVoucher(long amount) {
        Voucher voucher = new FixedDiscountVoucher(UUID.randomUUID(), amount);
        return voucherRepository.insert(voucher);
    }

    public Voucher createPercentDiscountVoucher(double percent) {
        Voucher voucher = new PercentDiscountVoucher(UUID.randomUUID(), (long) percent);
        return voucherRepository.insert(voucher);
    }

    public List<Voucher> fetchAllVouchers() {
        return new ArrayList<>(voucherRepository.getAllVouchersList());
    }
}
