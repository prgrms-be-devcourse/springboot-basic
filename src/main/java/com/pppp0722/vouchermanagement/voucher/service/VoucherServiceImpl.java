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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
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
                RuntimeException e = new RuntimeException(
                    "Invalid voucher type!");
                logger.error("Invalid voucher type!", e);
                throw e;
        }

        return voucherRepository.insert(voucher);
    }

    @Override
    public List<Voucher> getAllVouchers() {
        return voucherRepository.findAll();
    }

    @Override
    public List<Voucher> getVouchersByType(VoucherType type) {
        return voucherRepository.findByType(type);
    }

    @Override
    public Voucher getVoucherById(UUID voucherId) {
        return voucherRepository.findById(voucherId).orElseThrow(
            () -> new EmptyResultDataAccessException(1));
    }

    @Override
    public List<Voucher> getVouchersByMemberId(UUID memberId) {
        return voucherRepository.findByMemberId(memberId);
    }

    @Override
    public Voucher updateVoucher(UUID voucherId, VoucherType type, long amount) {
        Optional<Voucher> maybeVoucher = voucherRepository.findById(voucherId);
        if (maybeVoucher.isEmpty()) {
            throw new RuntimeException("Voucher does not exist.");
        }

        Voucher voucher = maybeVoucher.get();
        voucher.setType(type);
        voucher.setAmount(amount);

        return voucherRepository.update(voucher);
    }

    @Override
    public Voucher deleteVoucher(UUID voucherId) {
        Optional<Voucher> voucher = voucherRepository.findById(voucherId);

        if (voucher.isEmpty()) {
            throw new EmptyResultDataAccessException(1);
        }

        return voucherRepository.delete(voucher.get());
    }

    @Override
    public void deleteAllVouchers() {
        voucherRepository.deleteAll();
    }
}
