package kr.co.programmers.springbootbasic.voucher.dto.response;

import kr.co.programmers.springbootbasic.voucher.domain.VoucherType;

import java.time.LocalDateTime;
import java.util.UUID;

public class VoucherResponseDto {
    private final VoucherType type;
    private final UUID voucherId;
    private final long amount;
    private final LocalDateTime createdAt;

    public VoucherResponseDto(VoucherType type, UUID voucherId, long amount) {
        this.type = type;
        this.voucherId = voucherId;
        this.amount = amount;
        this.createdAt = null;
    }

    public VoucherResponseDto(VoucherType type, UUID voucherId, long amount, LocalDateTime createdAt) {
        this.type = type;
        this.voucherId = voucherId;
        this.amount = amount;
        this.createdAt = createdAt;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
