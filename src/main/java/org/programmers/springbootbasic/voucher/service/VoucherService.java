package org.programmers.springbootbasic.voucher.service;

import org.programmers.springbootbasic.voucher.model.Voucher;
import org.programmers.springbootbasic.voucher.model.VoucherType;
import org.programmers.springbootbasic.voucher.repository.VoucherRepository;
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

    public Optional<Voucher> getVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId);
    }

    public List<Voucher> getVoucherList() {
        return voucherRepository.findAll();
    }

    public Voucher createVoucher(VoucherType voucherType, UUID voucherId, long value, LocalDateTime createdAt) {
        var voucher = voucherType.create(voucherId, value, createdAt);
        return voucherRepository.insert(voucher);
    }

    public Voucher updateVoucher(UUID voucherId, long value) {
        Voucher voucher = this.getVoucher(voucherId).orElseThrow();
        VoucherType voucherType = VoucherType.findByType(String.valueOf(voucher.getVoucherType()));
        Voucher updateVoucher = voucherType.create(voucherId, value, voucher.getCreatedAt());
        return voucherRepository.update(updateVoucher);
    }

    public void deleteVoucher(UUID voucherId) {
        voucherRepository.deleteById(voucherId);
    }
}
