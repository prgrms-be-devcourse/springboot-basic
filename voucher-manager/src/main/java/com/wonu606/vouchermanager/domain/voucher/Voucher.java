package com.wonu606.vouchermanager.domain.voucher;

import com.wonu606.vouchermanager.domain.voucher.discountvalue.DiscountValue;
import com.wonu606.vouchermanager.domain.voucher.price.Price;
import java.util.Objects;
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

    public double getDiscountValue() {
        return discountValue.getValue();
    }

    @Override
    public String toString() {
        return "Voucher{" + "uuid=" + uuid + ", discountValue=" + discountValue + '}';
    }

    public abstract Price calculateDiscountedPrice(Price originalPrice);

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Voucher voucher = (Voucher) o;
        return getUuid().equals(voucher.getUuid())
                && Objects.equals(getDiscountValue(), voucher.getDiscountValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUuid(), getDiscountValue());
    }
}
