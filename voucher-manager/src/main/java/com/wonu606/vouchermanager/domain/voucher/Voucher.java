package com.wonu606.vouchermanager.domain.voucher;

import com.wonu606.vouchermanager.domain.voucher.discountvalue.DiscountValue;
import com.wonu606.vouchermanager.domain.voucher.price.Price;
import java.util.UUID;

public abstract class Voucher {

    protected final UUID uuid;
    protected final DiscountValue discountValue;

    public Voucher(UUID uuid, DiscountValue discountValue) {
        this.discountValue = discountValue;
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public DiscountValue getDiscountValue() {
        return discountValue;
    }

    @Override
    public String toString() {
        return "Voucher{" + "uuid=" + uuid + ", discountValue=" + discountValue + '}';
    }

    public abstract Price calculateDiscountedPrice(Price originalPrice);
}
