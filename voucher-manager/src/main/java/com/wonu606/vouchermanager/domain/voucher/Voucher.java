package com.wonu606.vouchermanager.domain.voucher;

import com.wonu606.vouchermanager.domain.discountvalue.DiscountValue;
import com.wonu606.vouchermanager.domain.price.Price;
import java.util.UUID;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public abstract class Voucher {

    protected final UUID uuid;
    protected final DiscountValue discountValue;

    public Voucher(UUID uuid, DiscountValue discountValue) {
        this.discountValue = discountValue;
        this.uuid = uuid;
    }

    public abstract double calculateDiscountedPrice(Price originalPrice);
}
