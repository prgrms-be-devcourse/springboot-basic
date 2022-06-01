package org.devcourse.voucher.application.voucher.controller.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.devcourse.voucher.application.voucher.model.Voucher;
import org.devcourse.voucher.application.voucher.model.VoucherType;

import java.util.UUID;

public class VoucherResponse {

    private final UUID voucherId;

    private final VoucherType voucherType;

    private final long discount;

    public VoucherResponse(UUID voucherID, VoucherType voucherType, long discount) {
        this.voucherId = voucherID;
        this.voucherType = voucherType;
        this.discount = discount;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public long getDiscount() {
        return discount;
    }

    public static VoucherResponse of(Voucher voucher) {
        return new VoucherResponse(
                voucher.getVoucherId(),
                VoucherType.nameDiscriminate(voucher.getClass().getSimpleName()),
                voucher.getDiscount()
        );
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("voucherId", voucherId)
                .append("voucherType", voucherType)
                .append("discount", discount)
                .toString();
    }
}
