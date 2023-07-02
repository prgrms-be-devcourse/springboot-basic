package com.wonu606.vouchermanager.domain.voucher;

import com.wonu606.vouchermanager.domain.discountvalue.PercentageDiscountValue;
import com.wonu606.vouchermanager.domain.price.Price;
import java.util.UUID;
import lombok.ToString;

@ToString
public class PercentageVoucher extends Voucher {

    public PercentageVoucher(UUID uuid, PercentageDiscountValue percentageDiscountValue) {
        super(uuid, percentageDiscountValue);
    }

    @Override
    public double calculateDiscountedPrice(Price originalPrice) {
        return originalPrice.getValue() * (1 - discountValue.getValue() / 100);
    }
}
