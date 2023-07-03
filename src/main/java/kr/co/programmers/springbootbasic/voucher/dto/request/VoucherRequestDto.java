package kr.co.programmers.springbootbasic.voucher.dto.request;

import kr.co.programmers.springbootbasic.voucher.domain.VoucherType;

public class VoucherRequestDto {
    private final VoucherType type;
    private final long amount;

    public VoucherRequestDto(VoucherType type, long amount) {
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
