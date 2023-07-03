package me.kimihiqq.vouchermanagement.domain.voucher;

import lombok.extern.slf4j.Slf4j;
import me.kimihiqq.vouchermanagement.option.VoucherTypeOption;

import java.util.UUID;

@Slf4j
public abstract class Voucher {

    private final UUID voucherId;
    private final VoucherTypeOption type;
    private final long discount;

    public Voucher(UUID voucherId, VoucherTypeOption type, long discount) {
        validateDiscount(discount);
        this.voucherId = voucherId;
        this.type = type;
        this.discount = discount;
    }

    private void validateDiscount(long discount) {
        if (discount <= 0) {
            throw new IllegalArgumentException("Discount must be positive.");
        }
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public String getType() {
        return type.name();
    }

    public long getDiscount() {
        return discount;
    }

    public abstract long discount(long beforeDiscount);

}