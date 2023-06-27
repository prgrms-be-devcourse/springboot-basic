package org.devcourse.voucher.domain.voucher;

import org.devcourse.voucher.domain.voucher.policy.Policy;

public class Voucher {
    private final long id;
    private final VoucherType type;
    private final Policy policy;

    public Voucher(long id, VoucherType type, Policy policy) {
        validate(type, policy);
        validateType(type);
        this.id = id;
        this.type = type;
        this.policy = policy;
    }

    public long getId() {
        return id;
    }

    public VoucherType getType() {
        return type;
    }

    public Policy getPolicy() {
        return policy;
    }

    public int useVoucherAndRetrieve(int receivedAmount) {
        return policy.execute(receivedAmount);
    }

    private void validate(VoucherType type, Policy policy) {
        validateType(type);
        validatePolicy(policy);
    }

    private void validateType(VoucherType type) {
        if (type == null) {
            throw new RuntimeException("바우처 타입은 빈 값일 수 없습니다");
        }
    }

    private void validatePolicy(Policy policy) {
        if (policy == null) {
            throw new RuntimeException("바우처 정책은 빈 값일 수 없습니다");
        }
    }
}
