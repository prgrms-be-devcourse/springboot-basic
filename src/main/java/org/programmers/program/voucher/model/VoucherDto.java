package org.programmers.program.voucher.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class VoucherDto {
    private final UUID id;
    private final VoucherType type;
    private final long amount;
    private final LocalDateTime expirationDate;

    public VoucherDto(UUID id, VoucherType type, long amount, LocalDateTime expirationDate) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.expirationDate = expirationDate;
    }

    public VoucherDto(UUID id, VoucherType type, long amount) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.expirationDate = LocalDateTime.now().plusWeeks(1);
    }

    public UUID getId() {
        return id;
    }

    public VoucherType getType() {
        return type;
    }

    public long getAmount() {
        return amount;
    }
    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public Voucher to(){
        if (type.equals(VoucherType.FIXED))
            return new FixedAmountVoucher(this);
        else
            return new PercentDiscountVoucher(this);
    }
}
