package org.prgrms.kdt.voucher;

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

    public void useVoucher(Voucher voucher) {
    }

    public Map<UUID, Voucher> getVoucherList() {
        return voucherRepository.getVoucherList();
    }

    public void createFixVoucher(String amount) {
        UUID voucherId = UUID.randomUUID();
        Voucher voucher = new FixedAmountVoucher(voucherId, Long.parseLong(amount));
        voucherRepository.insert(voucher);
    }

    public void createPercentVoucher(String percent) {
        UUID voucherId = UUID.randomUUID();
        Voucher voucher = new PercentDiscountVoucher(voucherId, Long.parseLong(percent));
        voucherRepository.insert(voucher);
    }

    public void createVoucher(VoucherType type, String amount) {
        if (type == VoucherType.FIXED) {
            createFixVoucher(amount);
        } else {
            createPercentVoucher(amount);
        }
    }
}
