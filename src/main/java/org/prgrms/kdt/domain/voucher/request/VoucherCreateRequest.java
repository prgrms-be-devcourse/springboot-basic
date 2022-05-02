package org.prgrms.kdt.domain.voucher.request;

import org.prgrms.kdt.domain.voucher.model.Voucher;
import org.prgrms.kdt.domain.voucher.model.VoucherType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.UUID;

public class VoucherCreateRequest {
    @NotNull
    private VoucherType voucherType;
    @NotNull
    @Positive
    private long discountValue;

    public VoucherCreateRequest() {
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public long getDiscountValue() {
        return discountValue;
    }

    public void setVoucherType(VoucherType voucherType) {
        this.voucherType = voucherType;
    }

    public void setDiscountValue(long discountValue) {
        this.discountValue = discountValue;
    }

    public Voucher toEntity() {
        return new Voucher(UUID.randomUUID(),
                voucherType,
                discountValue,
                LocalDateTime.now(),
                LocalDateTime.now());
    }
}
