package kr.co.programmers.springbootbasic.dto;

import kr.co.programmers.springbootbasic.voucher.VoucherType;

import java.util.UUID;

public class VoucherResponseDto {
    private final UUID voucherId;
    private final VoucherType type;
    private final long amount;

    public VoucherResponseDto(UUID voucherId, VoucherType type, long amount) {
        this.voucherId = voucherId;
        this.type = type;
        this.amount = amount;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public VoucherType getType() {
        return type;
    }

    public long getAmount() {
        return amount;
    }
}
