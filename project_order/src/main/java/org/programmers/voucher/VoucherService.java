package org.programmers.voucher;

import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher createFixedAmountVoucher(long amount) {
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), amount);
        voucherRepository.insert(voucher);
        return voucher;
    }

    public Voucher createPercentDiscountVoucher(long percent) {
        Voucher voucher = new PercentDiscountVoucher(UUID.randomUUID(), percent);
        voucherRepository.insert(voucher);
        return voucher;
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository.findById(voucherId)
                .orElseThrow(() -> new RuntimeException(MessageFormat.format("Can not find a voucher for {0}", voucherId)));
    }

    public void useVoucher(Voucher voucher) {
    }

}
