package org.prgrms.kdt.controller.voucher;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.UUID;

import static com.google.common.base.Preconditions.checkArgument;

public class UpdateVoucherRequest {
    private UUID voucherId;
    private long voucherValue;

    public UpdateVoucherRequest(UUID voucherId, long voucherValue) {
        checkArgument(voucherId != null, "voucher-id must be provided.");
        checkArgument(voucherValue > 0, "voucher-value must be greater than zero.");

        this.voucherId = voucherId;
        this.voucherValue = voucherValue;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public long getVoucherValue() {
        return voucherValue;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("voucherId", voucherId)
            .append("voucherValue", voucherValue)
            .toString();
    }
}
