package org.prgrms.kdt.domain.voucher.response;

import org.prgrms.kdt.domain.voucher.model.Voucher;
import org.prgrms.kdt.domain.voucher.model.VoucherType;

import java.time.LocalDateTime;
import java.util.UUID;

public class VoucherResponse {
    private UUID voucherId;
    private VoucherType voucherType;
    private Long discountValue;
    private UUID customerId;
    private LocalDateTime createdDateTime;
    private LocalDateTime modifiedDateTime;

    public VoucherResponse(Voucher voucher) {
        this.voucherId = voucher.getVoucherId();
        this.voucherType = voucher.getVoucherType();
        this.discountValue = voucher.getDiscountValue();
        this.customerId = voucher.getCustomerId();
        this.createdDateTime = voucher.getCreatedDateTime();
        this.modifiedDateTime = voucher.getModifiedDateTime();
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public Long getDiscountValue() {
        return discountValue;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public LocalDateTime getModifiedDateTime() {
        return modifiedDateTime;
    }
}
