package prgms.spring_week1.domain.voucher.model;

import prgms.spring_week1.domain.voucher.model.type.VoucherType;

import java.time.LocalDateTime;
import java.util.UUID;

public class Voucher {
    protected final UUID voucherId = UUID.randomUUID();
    protected final VoucherType voucherType;
    protected final int discount;
    protected final LocalDateTime createdAt = LocalDateTime.now();

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Voucher(VoucherType voucherType, int discount) {
        this.voucherType = voucherType;
        this.discount = discount;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public int getDiscount() {
        return discount;
    }
}
