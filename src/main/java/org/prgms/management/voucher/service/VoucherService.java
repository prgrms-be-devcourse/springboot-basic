package org.prgms.management.voucher.service;

import org.prgms.management.voucher.entity.Voucher;
import org.prgms.management.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Optional<Voucher> insert(Voucher voucher) {
        return voucherRepository.insert(voucher);
    }

    public Map<UUID, Voucher> getAll() {
        return voucherRepository.getAll();
    }

    public Optional<Voucher> getById(UUID voucherId) {
        return voucherRepository.getById(voucherId);
    }

    public Optional<Voucher> getByCreatedAt(LocalDateTime createdAt) {
        return voucherRepository.getByCreatedAt(createdAt);
    }

    public Optional<Voucher> getByType(String type) {
        return voucherRepository.getByType(type);
    }

    public Optional<Voucher> delete(UUID voucherId) {
        return voucherRepository.delete(voucherId);
    }

    public void deleteAll() {
        voucherRepository.deleteAll();
    }
}
