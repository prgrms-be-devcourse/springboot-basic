package com.programmers.voucher.service;

import com.programmers.voucher.domain.FixedAmountVoucher;
import com.programmers.voucher.domain.PercentDiscountVoucher;
import com.programmers.voucher.domain.Voucher;
import com.programmers.voucher.enums.VoucherType;
import com.programmers.voucher.repository.VoucherRepository;

import java.util.List;
import java.util.UUID;

public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public void createVoucher(String type, long amount) {
        VoucherType voucherType = VoucherType.getVoucherType(type);
        UUID voucherId = UUID.randomUUID();
        Voucher voucher;
        if (voucherType == VoucherType.PERCENT) {
            voucher = new PercentDiscountVoucher(voucherId, amount);
        }
        voucher = new FixedAmountVoucher(voucherId, amount);
        voucherRepository.save(voucher);
    }

    public List<Voucher> findVoucherList() {
        return voucherRepository.findAll();
    }
}
