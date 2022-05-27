package org.prgrms.kdt.controller.voucher;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.prgrms.kdt.model.voucher.Voucher;
import org.prgrms.kdt.model.voucher.VoucherType;

import java.time.LocalDateTime;
import java.util.UUID;

public class VoucherDto {
    private final UUID voucherId;
    private final long value;
    private final VoucherType voucherType;
    private final LocalDateTime createdAt;
    public VoucherDto(Voucher voucher) {
        this.voucherId = voucher.getId();
        this.value = voucher.getValue();
        this.createdAt = voucher.getCreatedAt();
        this.voucherType = VoucherType.getVoucherType(voucher.getClass());
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public long getValue() {
        return value;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("voucherId", voucherId)
            .append("value", value)
            .append("createdAt", createdAt)
            .append("voucherType", voucherType)
            .toString();
    }
}
