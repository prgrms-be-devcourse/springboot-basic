package org.programmers.program.voucher.model;

import java.time.LocalDate;
import java.util.UUID;

public abstract class Voucher {
    protected final UUID id;
    protected final Long discountAmount;
    protected final LocalDate expirationDate;

    protected Voucher(UUID id, Long discountAmount){
        this.id = id;
        this.discountAmount = discountAmount;
        expirationDate = LocalDate.now().plusWeeks(1);
    }
    protected Voucher(UUID id, Long discountAmount, LocalDate expirationDate) {
        this.id = id;
        this.discountAmount = discountAmount;
        this.expirationDate = expirationDate;
    }

    public UUID getVoucherId(){
        return this.id;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public Long getDiscountAmount(){
        return this.discountAmount;
    }

    abstract Long discount(Long price);
}
