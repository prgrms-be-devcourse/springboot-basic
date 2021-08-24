package org.prgrms.kdt.engine.voucher;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository; }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new RuntimeException("Cannot find the voucher for " + voucherId));
    }

    public void useVoucher(Voucher voucher) {
    }

    public Voucher createVoucher(VoucherType type, long rate) {
        var voucher = type.createVoucher(rate);
        voucherRepository.insert(voucher);
        return voucher;
    }

    public Optional<Map<UUID, Voucher>> listVoucher() {
        return voucherRepository.getAll();
    }
}
