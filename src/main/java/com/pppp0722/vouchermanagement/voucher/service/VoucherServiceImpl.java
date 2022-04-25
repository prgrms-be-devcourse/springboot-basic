package com.pppp0722.vouchermanagement.voucher.service;

import com.pppp0722.vouchermanagement.voucher.model.FixedAmountVoucher;
import com.pppp0722.vouchermanagement.voucher.model.PercentDiscountVoucher;
import com.pppp0722.vouchermanagement.voucher.model.Voucher;
import com.pppp0722.vouchermanagement.voucher.model.VoucherType;
import com.pppp0722.vouchermanagement.voucher.repository.VoucherRepository;
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

    public Optional<Voucher> createVoucher(UUID voucherId, VoucherType type, long amount,
        UUID memberId) {
        Voucher voucher = null;

        switch (type) {
            case FIXED_AMOUNT:
                voucher = new FixedAmountVoucher(voucherId, amount, memberId);
                break;
            case PERCENT_DISCOUNT:
                voucher = new PercentDiscountVoucher(voucherId, amount, memberId);
                break;
        }

        return voucherRepository.insert(voucher);
    }

    @Override
    public List<Voucher> getAllVouchers() {
        return voucherRepository.findAll();
    }

    @Override
    public Optional<Voucher> getVoucherByVoucherId(UUID voucherId) {
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

        switch (type) {
            case FIXED_AMOUNT:
                voucher = new FixedAmountVoucher(voucherId, amount, existing.get().getMemberId());
                break;
            case PERCENT_DISCOUNT:
                voucher = new PercentDiscountVoucher(voucherId, amount,
                    existing.get().getMemberId());
                break;
        }

        return voucherRepository.update(voucher);
    }

    @Override
    public Optional<Voucher> deleteVoucher(UUID voucherId) {
        Optional<Voucher> voucher = voucherRepository.findById(voucherId);

        if (voucher.isEmpty()) {
            return Optional.empty();
        }

        return voucherRepository.delete(voucher.get());
    }
}
