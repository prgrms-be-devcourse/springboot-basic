package org.prgrms.kdt.controller.voucher;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.prgrms.kdt.model.voucher.Voucher;
import org.prgrms.kdt.model.voucher.VoucherType;

import java.util.UUID;

public class VoucherDto {
    private final UUID voucherId;
    private final long value;
    private final VoucherType voucherType;

    public VoucherDto(Voucher voucher) {
        this.voucherId = voucher.getVoucherId();
        this.value = voucher.getVoucherValue();
        this.voucherType = VoucherType.getVoucherType(voucher.getClass());
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public long getValue() {
        return value;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("voucherId", voucherId)
            .append("value", value)
            .append("voucherType", voucherType)
            .toString();
    }
}
