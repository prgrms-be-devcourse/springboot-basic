package org.prgrms.vouchermanagement.service;

import org.prgrms.vouchermanagement.exception.InvalidRangeException;
import org.prgrms.vouchermanagement.repository.VoucherRepository;
import org.prgrms.vouchermanagement.voucher.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public void createVoucher(PolicyStatus policy, long amountOrPercent) {
        UUID uuid = UUID.randomUUID();
        DiscountPolicy discountPolicy = null;
        if (policy == PolicyStatus.FIXED) {
            discountPolicy = new FixedAmountVoucher(uuid, amountOrPercent, policy);
        } else if (policy == PolicyStatus.PERCENT) {
            validateAmountOrPercentRange(amountOrPercent);
            discountPolicy = new PercentDiscountVoucher(uuid, amountOrPercent, policy);
        }

        Voucher voucher = new Voucher(uuid, discountPolicy);
        voucherRepository.create(voucher);
    }

    public List<Voucher> voucherLists() {
        return voucherRepository.voucherLists();
    }

    private void validateAmountOrPercentRange(long amountOrPercent) {
        if (amountOrPercent < 0 || amountOrPercent > 100) {
            throw new InvalidRangeException("PercentDiscountPolicy는 0~100 사이의 값을 가져야 합니다.");
        }
    }

}
