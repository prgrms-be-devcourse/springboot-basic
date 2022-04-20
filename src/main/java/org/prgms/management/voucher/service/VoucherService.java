package org.prgms.management.voucher.service;

import org.prgms.management.voucher.entity.Voucher;
import org.prgms.management.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
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

    public List<Voucher> getAll() {
        return voucherRepository.findAll();
    }

    public Optional<Voucher> getById(UUID voucherId) {
        return voucherRepository.findById(voucherId);
    }

    public Optional<Voucher> getByCreatedAt(LocalDateTime createdAt) {
        return voucherRepository.findByCreatedAt(createdAt);
    }

    public Optional<Voucher> getByType(String type) {
        return voucherRepository.findByType(type);
    }

    public Optional<Voucher> update(Voucher voucher) {
        return voucherRepository.update(voucher);
    }

    public Optional<Voucher> delete(Voucher voucher) {
        return voucherRepository.delete(voucher);
    }

    public void deleteAll() {
        voucherRepository.deleteAll();
    }
}
