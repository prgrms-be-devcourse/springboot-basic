package org.devcourse.voucher.domain.voucher;

import org.devcourse.voucher.domain.voucher.policy.DiscountPolicy;

public class Voucher {
    private final long id;
    private final VoucherType type;
    private final DiscountPolicy policy;

    public Voucher(long id, VoucherType type, DiscountPolicy policy) {
        validate(type, policy);
        this.id = id;
        this.type = type;
        this.policy = policy;
    }

    private void validate(VoucherType type, DiscountPolicy policy) {
        validateType(type);
        validatePolicy(policy);
    }

    private void validateType(VoucherType type) {
        if (type == null) {
            throw new RuntimeException("바우처 타입은 빈 값일 수 없습니다");
        }
    }

    private void validatePolicy(DiscountPolicy policy) {
        if (policy == null) {
            throw new RuntimeException("바우처 정책은 빈 값일 수 없습니다");
        }
    }

    public int retrieveBalance(int receivedAmount) {
        return policy.discount(receivedAmount);
    }
}
