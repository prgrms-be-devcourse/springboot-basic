package com.prgms.missionW3D2;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher createVoucher(VoucherType voucherType , long figure) {
        if (voucherType == voucherType.FixedAmountVoucher) {
            Voucher newVoucher = new FixedAmountVoucher(UUID.randomUUID(), figure);
            voucherRepository.insert(newVoucher);
            return newVoucher;
        } else {
            Voucher newVoucher = new PercentDiscountVoucher(UUID.randomUUID(), figure);
            voucherRepository.insert(newVoucher);
            return newVoucher;
        }
    }

    public List<Voucher> getVoucherList() {
        return voucherRepository.voucherList();
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new RuntimeException(MessageFormat.format("can not find a voucher for {0}", voucherId)));
    }
}
