package org.prgms.management.voucher.service;

import org.prgms.management.voucher.entity.Voucher;
import org.prgms.management.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;

    }

    public boolean createVoucher(Voucher voucher) {
        return voucherRepository.save(voucher);
    }

    public Map<UUID, Voucher> getAllVouchers() {
        return voucherRepository.getAll();
    }
}
