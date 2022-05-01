package org.prgms.management.service.voucher;

import org.prgms.management.model.voucher.Voucher;
import org.prgms.management.model.voucher.VoucherCreator;
import org.prgms.management.repository.voucher.VoucherRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher create(String voucherName, String voucherType, int discountNum) {
        var voucher = VoucherCreator.createVoucher(
                discountNum, voucherName, voucherType);
        return voucherRepository.insert(voucher);
    }

    public List<Voucher> getAll() {
        return voucherRepository.findAll();
    }

    public Voucher getById(UUID voucherId) {
        return voucherRepository.findById(voucherId);
    }

    public List<Voucher> getByCreatedAt(LocalDateTime createdAt) {
        return voucherRepository.findByCreatedAt(createdAt);
    }

    public List<Voucher> getByType(String type) {
        return voucherRepository.findByType(type);
    }

    public Voucher getByName(String name) {
        return voucherRepository.findByName(name);
    }

    public Voucher update(Voucher voucher) {
        return voucherRepository.update(voucher);
    }

    public Voucher delete(Voucher voucher) {
        return voucherRepository.delete(voucher);
    }

    public Voucher delete(UUID id) {
        var voucher = voucherRepository.findById(id);
        if (voucher == null) {
            return null;
        }
        return voucherRepository.delete(voucher);
    }

    public void deleteAll() {
        voucherRepository.deleteAll();
    }
}
