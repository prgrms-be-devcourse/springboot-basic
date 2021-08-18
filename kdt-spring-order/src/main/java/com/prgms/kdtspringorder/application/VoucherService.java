package com.prgms.kdtspringorder.application;

import java.text.MessageFormat;
import java.util.UUID;

import com.prgms.kdtspringorder.domain.model.voucher.FixedAmountVoucher;
import com.prgms.kdtspringorder.domain.model.voucher.PercentDiscountVoucher;
import com.prgms.kdtspringorder.domain.model.voucher.Voucher;
import com.prgms.kdtspringorder.domain.model.voucher.VoucherRepository;
import com.prgms.kdtspringorder.domain.model.voucher.VoucherType;

public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher createVoucher(UUID voucherID, VoucherType type, long discount) {
        if (type == VoucherType.FIXED) {
            return new FixedAmountVoucher(voucherID, discount);
        }
        return new PercentDiscountVoucher(voucherID, discount);
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
            .findById(voucherId)
            .orElseThrow(() -> new RuntimeException(MessageFormat.format("Can not find a voucher for {0}", voucherId)));
    }

    public void useVoucher(UUID voucherId) {
        voucherRepository
            .findById(voucherId)
            .ifPresentOrElse(Voucher::useVoucher,
                () -> System.out.println("Can not find a voucher for " + voucherId));
    }
}
