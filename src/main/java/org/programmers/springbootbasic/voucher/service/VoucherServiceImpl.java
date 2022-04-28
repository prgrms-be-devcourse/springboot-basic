package org.programmers.springbootbasic.voucher.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.programmers.springbootbasic.voucher.domain.Voucher;
import org.programmers.springbootbasic.voucher.domain.VoucherProperty;
import org.programmers.springbootbasic.voucher.domain.VoucherType;
import org.programmers.springbootbasic.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class VoucherServiceImpl implements VoucherService {

    private final VoucherRepository voucherRepository;
    private final VoucherProperty voucherProperty;

    @Transactional
    @Override
    public Voucher createVoucher(int amount, VoucherType voucherType) {
        if (isValidAmount(amount, voucherType)) {
            return voucherRepository.insert(Voucher.create(amount, voucherType));
        }
        log.error("바우처의 할인 수치가 잘못되었습니다.={}", amount);
        throw new IllegalArgumentException("바우처의 할인 수치가 잘못되었습니다." + amount);
    }

    @Override
    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository.findById(voucherId).orElseThrow(
                () -> new IllegalArgumentException("No voucher found")
        );
    }

    @Override
    public List<Voucher> getVouchersByType(VoucherType type) {
        return voucherRepository.findByType(type);
    }

    @Override
    public List<Voucher> getVouchersByDate(Date startingDate, Date endDate) {
        return voucherRepository.findByDate(startingDate, endDate);
    }

    @Override
    public List<Voucher> getVouchersByTypeAndDate(VoucherType type, Date startingDate, Date endDate) {
        return voucherRepository.findByTypeAndDate(type, startingDate, endDate);
    }

    @Override
    public List<Voucher> getAllVouchers() {
        return voucherRepository.findAll();
    }

    @Transactional
    @Override
    public void registerVouchersOwner(UUID voucherId, Long memberId) {
        voucherRepository.updateVoucherOwner(voucherId, memberId);
    }

    @Transactional
    @Override
    public void deleteVoucher(UUID voucherId) {
        voucherRepository.remove(voucherId);
    }

    @Override
    public long applyVoucher(long beforeDiscount, Voucher voucher) {
        return voucher.discount(beforeDiscount);
    }

    @Override
    public boolean isValidAmount(int amount, VoucherType voucherType) {
        int minimum;
        int maximum;

        switch (voucherType) {
            case FIXED -> {
                minimum = voucherProperty.getFixed().minimumAmount();
                maximum = voucherProperty.getFixed().maximumAmount();
            }
            case RATE -> {
                minimum = voucherProperty.getRate().minimumAmount();
                maximum = voucherProperty.getRate().maximumAmount();
            }
            default -> {
                log.error("Invalid voucherType={}", voucherType);
                throw new IllegalArgumentException("Invalid voucherType=" + voucherType);
            }
        }
        return (amount >= minimum && amount <= maximum);
    }
}
