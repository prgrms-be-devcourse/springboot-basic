package me.kimihiqq.vouchermanagement.domain.voucher;

import lombok.extern.slf4j.Slf4j;
import me.kimihiqq.vouchermanagement.option.VoucherTypeOption;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
public abstract class Voucher {

    private final UUID voucherId;
    private final VoucherTypeOption type;
    private final long discount;
    private final LocalDateTime creationDateTime;


    public Voucher(UUID voucherId, VoucherTypeOption type, long discount) {
        validateDiscount(discount);
        this.voucherId = voucherId;
        this.type = type;
        this.discount = discount;
        this.creationDateTime = LocalDateTime.now();
    }


    protected abstract void validateDiscount(long discount);


    public UUID getVoucherId() {
        return voucherId;
    }

    public String getType() {
        return type.name();
    }

    public long getDiscount() {
        return discount;
    }

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }


    public abstract long discount(long beforeDiscount);

    @Override
    public String toString() {
        return voucherId + ": " + type + " - " + discount;
    }


}