package org.prgms.management.voucher.service;

import org.prgms.management.voucher.entity.Voucher;
import org.prgms.management.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

// TODO : CRUD 구현
@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Optional<Voucher> createVoucher(Voucher voucher) {
        return voucherRepository.insert(voucher);
    }

    public Map<UUID, Voucher> getAllVouchers() {
        return voucherRepository.getAll();
    }
}
