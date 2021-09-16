package org.prgrms.kdt.domain.voucher;

import java.time.LocalDateTime;

public class VoucherSearch {

    private VoucherType voucherType;
    private LocalDateTime createdAt;

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public void setVoucherType(VoucherType voucherType) {
        this.voucherType = voucherType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
