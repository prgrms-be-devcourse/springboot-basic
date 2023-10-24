package org.prgrms.vouchermanagement.service;

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
        UUID voucherId = UUID.randomUUID();

        DiscountPolicy discountPolicy = getDiscountPolicy(policy, amountOrPercent);

        voucherRepository.create(voucherId, discountPolicy);
    }

    public List<Voucher> voucherLists() {
        return voucherRepository.voucherLists();
    }

    private static DiscountPolicy getDiscountPolicy(PolicyStatus policy, long amountOrPercent) {
        DiscountPolicy discountPolicy = null;
        if (policy == PolicyStatus.FIXED) {
            discountPolicy = new FixedAmountVoucher(amountOrPercent);
        } else if (policy == PolicyStatus.PERCENT) {
            discountPolicy = new PercentDiscountVoucher(amountOrPercent);
        }
        return discountPolicy;
    }
}
