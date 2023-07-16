package prgms.spring_week1.domain.voucher.model;

import prgms.spring_week1.domain.voucher.model.type.VoucherType;

import java.time.LocalDateTime;
import java.util.UUID;

public class Voucher {
    private final UUID voucherId = UUID.randomUUID();
    private final VoucherType voucherType;
    private final int discount;
    private final LocalDateTime createdAt = LocalDateTime.now();

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
