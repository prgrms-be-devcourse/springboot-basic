package org.prgrms.kdt.shop.dto;

import org.prgrms.kdt.shop.enums.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.UUID;

public class VoucherCreateServiceDto {

    private final UUID voucherId;
    private final long amount;
    private final VoucherType type;
    private final LocalDateTime createdAt;
    private static final Logger logger = LoggerFactory.getLogger(VoucherCreateServiceDto.class);

    public VoucherCreateServiceDto(UUID voucherId, long amount, String type,
        LocalDateTime createdAt) {
        this.voucherId = voucherId;
        this.amount = amount;
        this.createdAt = createdAt;
        try {
            this.type = VoucherType.find(type);
        } catch (IllegalArgumentException e) {
            logger.error("invalid voucher type.", e);
            throw new IllegalArgumentException("invalid voucher type.");
        }
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public long getAmount() {
        return amount;
    }

    public VoucherType getType() {
        return type;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
