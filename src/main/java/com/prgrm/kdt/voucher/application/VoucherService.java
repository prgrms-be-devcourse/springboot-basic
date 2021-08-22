package com.prgrm.kdt.voucher.application;

import com.prgrm.kdt.voucher.domain.FixedAmountVoucher;
import com.prgrm.kdt.voucher.domain.PercentDiscountVoucher;
import com.prgrm.kdt.voucher.domain.Voucher;
import com.prgrm.kdt.voucher.domain.VoucherType;
import com.prgrm.kdt.voucher.repository.VoucherRepository;

import java.text.MessageFormat;
import java.util.Map;
import java.util.UUID;

import static com.prgrm.kdt.voucher.domain.VoucherType.*;

public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new RuntimeException(MessageFormat.format("Can not find a voucher for {0}", voucherId)));
    }

    public void useVoucher(Voucher voucher) {
    }

    public Map<UUID, Voucher> findAllVoucher() {
        return voucherRepository.findAll();
    }

    public Voucher createVoucher(VoucherType type, Long value) {
        if (type == FIXED) {
            return new FixedAmountVoucher(UUID.randomUUID(), value);
        }
        return new PercentDiscountVoucher(UUID.randomUUID(), value);
    }

    public void insertVoucher(Voucher voucher) {
        voucherRepository.insert(voucher);
    }

    public VoucherType selectVoucherType(String input) {
        return findByVoucherType(input);
    }
}
