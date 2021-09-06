package org.prgrms.kdt.voucher.service;

import org.prgrms.kdt.voucher.model.FixedAmountVoucher;
import org.prgrms.kdt.voucher.model.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.model.Voucher;
import org.prgrms.kdt.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

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

    public void useVoucher(Voucher voucher) {

    }
}
