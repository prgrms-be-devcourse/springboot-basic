package com.pppp0722.vouchermanagement.voucher.service;

import com.pppp0722.vouchermanagement.voucher.model.FixedAmountVoucher;
import com.pppp0722.vouchermanagement.voucher.model.PercentDiscountVoucher;
import com.pppp0722.vouchermanagement.voucher.model.Voucher;
import com.pppp0722.vouchermanagement.voucher.model.VoucherType;
import com.pppp0722.vouchermanagement.voucher.repository.VoucherRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class VoucherServiceImpl implements VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherServiceImpl(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Optional<Voucher> createVoucher(UUID voucherId, VoucherType type, long amount, LocalDateTime createdAt, UUID memberId) {
        Voucher voucher = null;

        switch (type) {
            case FIXED_AMOUNT:
                voucher = new FixedAmountVoucher(voucherId, amount, createdAt, memberId);
                break;
            case PERCENT_DISCOUNT:
                voucher = new PercentDiscountVoucher(voucherId, amount, createdAt, memberId);
                break;
            case NONE:
                return Optional.empty();
        }

        return voucherRepository.insert(voucher);
    }

    @Override
    public List<Voucher> getAllVouchers() {
        return voucherRepository.findAll();
    }

    @Override
    public Optional<Voucher> getVoucherById(UUID voucherId) {
        return voucherRepository.findById(voucherId);
    }

    @Override
    public List<Voucher> getVouchersByMemberId(UUID memberId) {
        return voucherRepository.findByMemberId(memberId);
    }

    @Override
    public Optional<Voucher> updateVoucher(UUID voucherId, VoucherType type, long amount) {
        Voucher voucher = null;

        Optional<Voucher> existing = voucherRepository.findById(voucherId);

        if (existing.isEmpty()) {
            return Optional.empty();
        }

        return voucherRepository.update(existing.get());
    }

    @Override
    public Optional<Voucher> deleteVoucher(UUID voucherId) {
        Optional<Voucher> voucher = voucherRepository.findById(voucherId);

        if (voucher.isEmpty()) {
            return Optional.empty();
        }

        return voucherRepository.delete(voucher.get());
    }

    @Override
    public void deleteAll() {
        voucherRepository.deleteAll();
    }
}
