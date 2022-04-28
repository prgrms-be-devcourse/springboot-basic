package com.pppp0722.vouchermanagement.voucher.service;

import com.pppp0722.vouchermanagement.exception.InvalidVoucherTypeException;
import com.pppp0722.vouchermanagement.voucher.model.FixedAmountVoucher;
import com.pppp0722.vouchermanagement.voucher.model.PercentDiscountVoucher;
import com.pppp0722.vouchermanagement.voucher.model.Voucher;
import com.pppp0722.vouchermanagement.voucher.model.VoucherType;
import com.pppp0722.vouchermanagement.voucher.repository.VoucherRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class VoucherServiceImpl implements VoucherService {

    private static final Logger logger = LoggerFactory.getLogger(VoucherServiceImpl.class);

    private final VoucherRepository voucherRepository;

    public VoucherServiceImpl(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher createVoucher(UUID voucherId, VoucherType type, long amount,
        LocalDateTime createdAt, UUID memberId) {
        Voucher voucher;
        switch (type) {
            case FIXED_AMOUNT:
                voucher = new FixedAmountVoucher(voucherId, amount, createdAt, memberId);
                break;
            case PERCENT_DISCOUNT:
                voucher = new PercentDiscountVoucher(voucherId, amount, createdAt, memberId);
                break;
            default:
                throw new InvalidVoucherTypeException("Invalid voucher type!");
        }

        return voucherRepository.insert(voucher);
    }

    @Override
    public List<Voucher> getAllVouchers() {
        return voucherRepository.findAll();
    }

    @Override
    public Voucher getVoucherById(UUID voucherId) {
        Optional<Voucher> voucher = voucherRepository.findById(voucherId);

        if (voucher.isEmpty()) {
            throw new RuntimeException("Voucher does not exist.");
        }

        return voucher.get();
    }

    @Override
    public List<Voucher> getVouchersByMemberId(UUID memberId) {
        return voucherRepository.findByMemberId(memberId);
    }

    @Override
    public Voucher updateVoucher(UUID voucherId, VoucherType type, long amount) {
        Optional<Voucher> voucher = voucherRepository.findById(voucherId);

        if (voucher.isEmpty()) {
            throw new RuntimeException("Voucher does not exist.");
        }

        return voucherRepository.update(voucher.get());
    }

    @Override
    public Voucher deleteVoucher(UUID voucherId) {
        Optional<Voucher> voucher = voucherRepository.findById(voucherId);

        if (voucher.isEmpty()) {
            throw new RuntimeException("Voucher does not exist.");
        }

        return voucherRepository.delete(voucher.get());
    }

    @Override
    public void deleteAllVouchers() {
        voucherRepository.deleteAll();
    }
}
