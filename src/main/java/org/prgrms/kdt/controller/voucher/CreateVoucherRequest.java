package org.prgrms.kdt.controller.voucher;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.prgrms.kdt.model.voucher.VoucherType;

import static com.google.common.base.Preconditions.checkArgument;

public class CreateVoucherRequest {
    private long voucherValue;
    private VoucherType voucherType;

    public CreateVoucherRequest(long voucherValue, VoucherType voucherType) {
        checkArgument(voucherValue > 0, "voucher-value must be greater than zero.");
        checkArgument(voucherType != null, "voucher-type must be provided.");

        this.voucherValue = voucherValue;
        this.voucherType = voucherType;
    }

    public long getVoucherValue() {
        return voucherValue;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("voucherValue", voucherValue)
            .append("voucherType", voucherType)
            .toString();
    }
}
