package org.prgrms.kdtspringdemo.voucher;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.UUID;

@Service
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

    // 이번 요구사항에 포함되지 않았습니다.
    public void useVourcher(Voucher voucher) {
    }

    public Voucher createFixedAmountVoucher(UUID randomUUID, long amount) {
        var voucher = new FixedAmountVoucher(randomUUID, amount);
        voucherRepository.insert(voucher);
        return voucher;
    }

    public Voucher createPercentDiscountVoucher(UUID randomUUID, long percent) {
        var voucher = new PercentDiscountVoucher(randomUUID, percent);
        voucherRepository.insert(voucher);
        return voucher;
    }
}
