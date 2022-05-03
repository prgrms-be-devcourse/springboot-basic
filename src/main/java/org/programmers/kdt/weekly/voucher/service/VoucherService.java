package org.programmers.kdt.weekly.voucher.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.programmers.kdt.weekly.voucher.VoucherDto;
import org.programmers.kdt.weekly.voucher.model.Voucher;
import org.programmers.kdt.weekly.voucher.model.VoucherType;
import org.programmers.kdt.weekly.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher create(UUID voucherId, VoucherType voucherType, long value) {
        VoucherDto voucherDto = new VoucherDto(voucherId, value, LocalDateTime.now());

        return this.voucherRepository.insert(voucherType.create(voucherDto));
    }

    public Voucher create(VoucherType voucherType, long value) {
        VoucherDto voucherDto = new VoucherDto(UUID.randomUUID(), value, LocalDateTime.now());

        return this.voucherRepository.insert(voucherType.create(voucherDto));
    }

    public List<Voucher> getVouchers() {
        return this.voucherRepository.findAll();
    }

    public Optional<Voucher> findById(UUID id) {
        return this.voucherRepository.findById(id);
    }

    public boolean deleteById(UUID id) {
        return this.voucherRepository.deleteById(id);
    }

    public void update(Voucher voucher) {
        this.voucherRepository.update(voucher);
    }

    public List<Voucher> findByType(VoucherType voucherType) {
        return this.voucherRepository.findByType(voucherType);
    }

    public List<Voucher> getVoucherByCreatedAt(LocalDate begin, LocalDate end) {
        return this.voucherRepository.findByCreatedAt(begin, end);
    }
}