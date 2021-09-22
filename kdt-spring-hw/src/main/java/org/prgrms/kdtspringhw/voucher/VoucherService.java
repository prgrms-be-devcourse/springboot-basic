package org.prgrms.kdtspringhw.voucher;

import org.prgrms.kdtspringhw.voucher.voucherObj.FixedAmountVoucher;
import org.prgrms.kdtspringhw.voucher.voucherObj.PercentDiscountVoucher;
import org.prgrms.kdtspringhw.voucher.voucherObj.Voucher;
import org.prgrms.kdtspringhw.voucher.voucherObj.VoucherType;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Map;
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

    public boolean createVoucher(VoucherType voucherType) {
        Voucher voucher;
        switch (voucherType) {
            case FIXED_AMOUNT_VOUCHER:
                voucher = voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), 10L));
                return true;
            case PERCENT_DISCOUNT_VOUCHER:
                voucher = voucherRepository.insert(new PercentDiscountVoucher(UUID.randomUUID(), 10));
                return true;
            default:
                return false;
        }
    }

    public Map<UUID, Voucher> getVouchers() {
        return voucherRepository.returnAll();
    }
}
