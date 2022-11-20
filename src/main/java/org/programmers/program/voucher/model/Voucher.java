package org.programmers.program.voucher.model;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class Voucher {
    protected final UUID id;
    protected final Long discountAmount;

    private final LocalDateTime createdAt;
    protected final LocalDateTime expirationDate;

    protected VoucherType voucherType = null;
    protected boolean isUsed;

    protected Voucher(UUID id, Long discountAmount){
        this.id = id;
        this.discountAmount = discountAmount;
        createdAt = LocalDateTime.now();
        expirationDate = LocalDateTime.now().plusWeeks(1);
        isUsed = false;
    }
    protected Voucher(UUID id, Long discountAmount, LocalDateTime expirationDate) {
        this.id = id;
        this.discountAmount = discountAmount;
        this.expirationDate = expirationDate;
        createdAt = LocalDateTime.now();
        isUsed = false;
    }

    public UUID getVoucherId(){
        return this.id;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public Long getDiscountAmount(){
        return this.discountAmount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    abstract Long discount(Long price);

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public boolean getIsUsed(){
        return isUsed;
    }
}
