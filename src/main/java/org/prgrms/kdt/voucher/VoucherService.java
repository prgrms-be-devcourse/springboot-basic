package org.prgrms.kdt.voucher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
                .orElseThrow(() -> new RuntimeException("Can not find a voucher for " + voucherId));
    }

    public Voucher createFixedAmountVoucher(int fixedAmount) {
        var voucher = new FixedAmountVoucher(UUID.randomUUID(), fixedAmount);
        voucherRepository.insert(voucher);
        return voucher;
    }

    public Voucher createPercentDiscountVoucher(int percentDiscount) {
        var voucher = new PercentDiscountVoucher(UUID.randomUUID(), percentDiscount);
        voucherRepository.insert(voucher);
        return voucher;
    }

    public Map<UUID, Voucher> getVoucherList() {
        return voucherRepository.getStorage();
    }
}
