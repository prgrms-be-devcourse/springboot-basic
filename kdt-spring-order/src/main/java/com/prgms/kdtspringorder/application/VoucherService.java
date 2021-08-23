package com.prgms.kdtspringorder.application;

import java.text.MessageFormat;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.prgms.kdtspringorder.domain.model.voucher.FixedAmountVoucher;
import com.prgms.kdtspringorder.domain.model.voucher.PercentDiscountVoucher;
import com.prgms.kdtspringorder.domain.model.voucher.Voucher;
import com.prgms.kdtspringorder.domain.model.voucher.VoucherRepository;
import com.prgms.kdtspringorder.domain.model.voucher.VoucherType;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher create(UUID voucherID, VoucherType type, long discount) {
        Voucher voucher = null;
        if (type == VoucherType.FIXED) {
            voucher = new FixedAmountVoucher(voucherID, discount);
        } else if (type == VoucherType.PERCENT) {
            voucher = new PercentDiscountVoucher(voucherID, discount);
        }
        return voucherRepository.save(voucher);
    }

    public Voucher findById(UUID voucherId) {
        return voucherRepository
            .findById(voucherId)
            .orElseThrow(() -> new RuntimeException(MessageFormat.format("Can not find a voucher for {0}", voucherId)));
    }

    public void useVoucher(UUID voucherId) {
        voucherRepository
            .findById(voucherId)
            .ifPresentOrElse(Voucher::useVoucher,
                () -> new RuntimeException(MessageFormat.format("Can not find a voucher for {0}", voucherId)));
    }

    public Map<UUID, Voucher> findAll() {
        return voucherRepository.findAll();
    }
}
