package kr.co.programmers.springbootbasic.dto;

import kr.co.programmers.springbootbasic.voucher.VoucherType;

import java.util.UUID;

public class VoucherResponseDto {
    private final VoucherType type;
    private final UUID voucherId;
    private final long amount;

    public VoucherResponseDto(VoucherType type, UUID voucherId, long amount) {
        this.type = type;
        this.voucherId = voucherId;
        this.amount = amount;
    }

    public VoucherType getType() {
        return type;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public long getAmount() {
        return amount;
    }
}
