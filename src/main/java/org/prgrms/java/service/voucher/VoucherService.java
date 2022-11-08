package org.prgrms.java.service.voucher;

import org.prgrms.java.domain.voucher.Voucher;
import org.prgrms.java.repository.voucher.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher createVoucher(Voucher voucher) {
        return voucherRepository.insert(voucher);
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository.findById(voucherId)
                .orElseThrow(() -> new RuntimeException(String.format("Can not find a voucher for %s", voucherId)));
    }

    public Collection<Voucher> getAllVoucher() {
        return voucherRepository.findAll();
    }
}
