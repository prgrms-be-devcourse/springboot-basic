package kr.co.programmers.springbootbasic.voucher.dto;

import kr.co.programmers.springbootbasic.voucher.domain.VoucherType;

public class VoucherCreationRequestDto {
    private final VoucherType type;
    private final long amount;

    public VoucherCreationRequestDto(VoucherType type, long amount) {
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
