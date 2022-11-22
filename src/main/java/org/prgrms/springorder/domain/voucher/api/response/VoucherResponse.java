package org.prgrms.springorder.domain.voucher.api.response;

import java.time.LocalDateTime;
import java.util.UUID;
import org.prgrms.springorder.domain.voucher.model.VoucherType;

public class VoucherResponse {

    private final UUID voucherId;

    private final long amount;

    private final UUID customerId;

    private final LocalDateTime createdAt;

    private final VoucherType voucherType;

    public VoucherResponse(UUID voucherId, long amount, UUID customerId,
        LocalDateTime createdAt,
        VoucherType voucherType) {
        this.voucherId = voucherId;
        this.amount = amount;
        this.customerId = customerId;
        this.createdAt = createdAt;
        this.voucherType = voucherType;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public long getAmount() {
        return amount;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }
}
