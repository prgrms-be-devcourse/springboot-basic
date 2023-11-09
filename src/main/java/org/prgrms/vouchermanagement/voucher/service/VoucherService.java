package org.prgrms.vouchermanagement.voucher.service;

import org.prgrms.vouchermanagement.voucher.domain.Voucher;
import org.prgrms.vouchermanagement.voucher.policy.DiscountPolicy;
import org.prgrms.vouchermanagement.voucher.policy.FixedAmountVoucher;
import org.prgrms.vouchermanagement.voucher.policy.PercentDiscountVoucher;
import org.prgrms.vouchermanagement.voucher.policy.PolicyStatus;
import org.prgrms.vouchermanagement.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    /*
    pr
    model 비즈니스 로직
    view 뿌리기
    controller 전달
     */

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher createVoucher(PolicyStatus policy, long amountOrPercent) {
        UUID voucherId = UUID.randomUUID();
        DiscountPolicy discountPolicy = getDiscountPolicy(policy, amountOrPercent);
        voucherRepository.create(voucherId, discountPolicy);
        return new Voucher(voucherId, discountPolicy);
    }

    public Voucher findVoucher(UUID voucherId) {
        return voucherRepository.findById(voucherId);
    }

    public List<Voucher> voucherLists() {
        return voucherRepository.voucherLists();
    }

    public Voucher updateVoucher(UUID voucherId, long amountOrPercent) {
        voucherRepository.update(voucherId, amountOrPercent);
        DiscountPolicy discountPolicy = getDiscountPolicy(findVoucher(voucherId).getDiscountPolicy().getPolicyStatus(), amountOrPercent);
        return new Voucher(voucherId, discountPolicy);
    }

    public Voucher deleteVoucher(UUID voucherId) {
        Voucher voucher = voucherRepository.findById(voucherId);
        voucherRepository.delete(voucherId);
        return voucher;
    }

    public List<Voucher> findVouchersByPolicy(PolicyStatus policy) {
        return voucherRepository.findVoucherByPolicy(policy);
    }

    private DiscountPolicy getDiscountPolicy(PolicyStatus policy, long amountOrPercent) {
        DiscountPolicy discountPolicy = null;
        if (policy == PolicyStatus.FIXED) {
            discountPolicy = new FixedAmountVoucher(amountOrPercent);
        } else if (policy == PolicyStatus.PERCENT) {
            discountPolicy = new PercentDiscountVoucher(amountOrPercent);
        }
        return discountPolicy;
    }
}
