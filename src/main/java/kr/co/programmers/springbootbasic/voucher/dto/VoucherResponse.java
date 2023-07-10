package kr.co.programmers.springbootbasic.voucher.dto;

import kr.co.programmers.springbootbasic.voucher.domain.VoucherType;

import java.time.LocalDateTime;
import java.util.UUID;

public class VoucherResponse {
    private final VoucherType type;
    private final UUID voucherId;
    private final Long amount;
    private final LocalDateTime createdAt;
    private final UUID walletId;

    public VoucherResponse(UUID voucherId) {
        this.type = null;
        this.voucherId = voucherId;
        this.amount = null;
        this.createdAt = null;
        this.walletId = null;
    }

    public VoucherResponse(VoucherType type, UUID voucherId, Long amount, LocalDateTime createdAt, UUID walletId) {
        this.type = type;
        this.voucherId = voucherId;
        this.amount = amount;
        this.createdAt = createdAt;
        this.walletId = walletId;
    }

    public VoucherType getType() {
        return type;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public Long getAmount() {
        return amount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public UUID getWalletId() {
        return walletId;
    }
}
