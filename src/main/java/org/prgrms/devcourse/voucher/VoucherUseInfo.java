package org.prgrms.devcourse.voucher;

import java.time.LocalDateTime;
import java.util.UUID;

public class VoucherUseInfo {
    private final UUID voucherUseInfoId;
    private final UUID customerId;
    private final UUID voucherId;
    private final LocalDateTime issuedAt;
    private LocalDateTime expiredAt;
    private boolean isUsed;

    public VoucherUseInfo(UUID voucherUseInfoId, UUID customerId, UUID voucherId) {
        this.voucherUseInfoId = voucherUseInfoId;
        this.customerId = customerId;
        this.voucherId = voucherId;
        this.issuedAt = LocalDateTime.now();
        this.expiredAt = LocalDateTime.now().plusMonths(1);
        this.isUsed = false;
    }

    public VoucherUseInfo(UUID voucherUseInfoId, UUID customerId, UUID voucherId, LocalDateTime issuedAt, LocalDateTime expiredAt, boolean isUsed) {
        this.voucherUseInfoId = voucherUseInfoId;
        this.customerId = customerId;
        this.voucherId = voucherId;
        this.issuedAt = issuedAt;
        this.expiredAt = expiredAt;
        this.isUsed = isUsed;
        validateDate();
    }

    public void validateDate() {
        if (this.issuedAt.isAfter(this.expiredAt))
            throw new RuntimeException("Invalid Expired Date");
    }

    public boolean isUsable() {
        return expiredAt.isAfter(LocalDateTime.now()) && !isUsed;
    }


    public UUID getVoucherUseInfoId() {
        return voucherUseInfoId;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public LocalDateTime getIssuedAt() {
        return issuedAt;
    }

    public LocalDateTime getExpiredAt() {
        return expiredAt;
    }

    public boolean isUsed() {
        return isUsed;
    }
}
