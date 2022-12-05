package org.prgrms.java.domain.voucher;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.UUID;

public class CreateVoucherRequest {
    private final UUID ownerId;
    private final long amount;
    private final String type;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private final LocalDateTime expiredAt;

    public CreateVoucherRequest(UUID ownerId, long amount, String type, LocalDateTime expiredAt) {
        this.ownerId = ownerId;
        this.amount = amount;
        this.type = type;
        this.expiredAt = expiredAt;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public long getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

    public LocalDateTime getExpiredAt() {
        return expiredAt;
    }
}
