package kr.co.programmers.springbootbasic.voucher.dto;

import kr.co.programmers.springbootbasic.voucher.domain.VoucherType;

public class VoucherCreateRequest {
    private final VoucherType type;
    private final long amount;

    public VoucherCreateRequest(VoucherType type, long amount) {
        this.type = type;
        this.amount = amount;
    }

    public VoucherType getType() {
        return type;
    }

    public long getAmount() {
        return amount;
    }
}
