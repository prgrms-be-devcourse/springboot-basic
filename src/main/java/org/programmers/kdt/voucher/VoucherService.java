package org.programmers.kdt.voucher;

import java.text.MessageFormat;
import java.util.UUID;

public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher getVoucher(UUID voucherId) {
        return this.voucherRepository
                .findById(voucherId)
                .orElseThrow( () -> new RuntimeException(MessageFormat.format("Cannot find a voucher for {0}", voucherId)) );
    }

    public void useVoucher(Voucher voucher) {
    }

    public void addVoucher(Voucher voucher) {
        this.voucherRepository.addVoucher(voucher);
    }
}
