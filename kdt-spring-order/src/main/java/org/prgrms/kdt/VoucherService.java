package org.prgrms.kdt;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

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

    public List<Voucher> getVoucherList() {
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
}
