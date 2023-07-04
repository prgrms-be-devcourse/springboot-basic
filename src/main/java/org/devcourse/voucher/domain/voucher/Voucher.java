package org.devcourse.voucher.domain.voucher;

import org.devcourse.voucher.domain.voucher.policy.DiscountPolicy;
import org.devcourse.voucher.domain.voucher.vo.VoucherVO;

public class Voucher {
    private final long id;
    private final VoucherType type;
    private final DiscountPolicy policy;

    public Voucher(long id, VoucherType type, int amount) {
        validateType(type);
        this.id = id;
        this.type = type;
        this.policy = type.createPolicy(amount);
    }

    private void validateType(VoucherType type) {
        if (type == null) {
            throw new RuntimeException("바우처 타입은 빈 값일 수 없습니다");
        }
    }

    public long getId() {
        return id;
    }

    public int retrieveBalance(int receivedAmount) {
        return policy.discount(receivedAmount);
    }

    public Voucher newInstanceWithId(long id) {
        return new Voucher(id, this.type, this.policy.getAmount());
    }

    public VoucherVO values() {
        return new VoucherVO(id, type.name(), policy.getAmount());
    }
}
