package org.programmers.springbootbasic.voucher.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.programmers.springbootbasic.voucher.domain.*;
import org.programmers.springbootbasic.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static org.programmers.springbootbasic.voucher.domain.VoucherType.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class VoucherServiceImpl implements VoucherService {

    private final VoucherRepository voucherRepository;
    private final VoucherProperty voucherProperty;

    @Override
    public Voucher registerVoucher(int amount, VoucherType voucherType) {
        if (isValidAmount(amount, voucherType)) {
            Voucher voucher = createVoucher(amount, voucherType);
            return voucherRepository.insert(voucher);
        }
        log.error("바우처의 할인 수치가 잘못되었습니다.={}", amount);
        throw new IllegalArgumentException("바우처의 할인 수치가 잘못되었습니다." + amount);
    }

    private Voucher createVoucher(int amount, VoucherType voucherType) {
        if (voucherType == FIXED) {
            return new FixedDiscountVoucher(UUID.randomUUID(), amount);
        }
        if (voucherType == RATE) {
            return new RateDiscountVoucher(UUID.randomUUID(), amount);
        }
        log.error("Illegal type of voucher. No corresponding voucher type exist.");
        throw new IllegalArgumentException("유효하지 않은 바우처 종류를 만들려고 시도했습니다.");
    }

    @Override
    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository.findById(voucherId).orElseThrow(
                () -> new IllegalArgumentException("No voucher found")
        );
    }

    @Override
    public long applyVoucher(long beforeDiscount, Voucher voucher) {
        return voucher.discount(beforeDiscount);
    }

    @Override
    public void useVoucher(UUID voucherId) {
        voucherRepository.remove(voucherId);
    }

    @Override
    public List<Voucher> getAllVouchers() {
        return voucherRepository.findAll();
    }

    public boolean isValidAmount(int amount, VoucherType voucherType) {
        int minimum;
        int maximum;

        switch (voucherType) {
            case FIXED:
                minimum = voucherProperty.getFixed().minimumAmount();
                maximum = voucherProperty.getFixed().maximumAmount();
                break;
            case RATE:
                minimum = voucherProperty.getRate().minimumAmount();
                maximum = voucherProperty.getRate().maximumAmount();
                break;
            default:
                log.error("Invalid voucherType={}", voucherType);
                throw new IllegalArgumentException("Invalid voucherType=" + voucherType);
        }
        return (amount >= minimum && amount <= maximum);
    }
}
