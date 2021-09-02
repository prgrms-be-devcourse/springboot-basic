package org.prgrms.kdt.kdtspringorder.voucher.domain;

import org.prgrms.kdt.kdtspringorder.common.enums.VoucherType;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 바우처 추상클래스
 */
public abstract class Voucher implements Discountable, Serializable {

    private final UUID voucherId;

    private UUID customerId;

    protected VoucherType voucherType;

    private String useYn;

    private LocalDateTime createdAt;

    private LocalDateTime usedAt;

    protected Voucher(UUID voucherId) {
        this.voucherId = voucherId;
    }

    public Voucher(UUID voucherId, UUID customerId, VoucherType voucherType, String useYn, LocalDateTime createdAt, LocalDateTime usedAt) {
        this.voucherId = voucherId;
        this.customerId = customerId;
        this.voucherType = voucherType;
        this.useYn = useYn;
        this.createdAt = createdAt;
        this.usedAt = usedAt;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public String getUseYn() {
        return useYn;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUsedAt() {
        return usedAt;
    }

}
