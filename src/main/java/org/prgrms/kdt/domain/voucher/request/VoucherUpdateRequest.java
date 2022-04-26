package org.prgrms.kdt.domain.voucher.request;

import org.prgrms.kdt.domain.voucher.model.Voucher;
import org.prgrms.kdt.domain.voucher.model.VoucherType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.UUID;

public class VoucherUpdateRequest {
    @NotNull
    private UUID voucherId;
    @NotNull
    private VoucherType voucherType;
    @NotNull
    @Positive
    private long discountValue;

    public VoucherUpdateRequest(UUID voucherId, String voucherType, long discountValue) {
        this.voucherType = VoucherType.findVoucherType(voucherType);
        this.voucherId = voucherId;
        this.discountValue = discountValue;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public long getDiscountValue() {
        return discountValue;
    }

}
