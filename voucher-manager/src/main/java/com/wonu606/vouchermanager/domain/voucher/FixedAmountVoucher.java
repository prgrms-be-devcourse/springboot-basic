package com.wonu606.vouchermanager.domain.voucher;

import com.wonu606.vouchermanager.domain.discountvalue.FixedAmountValue;
import com.wonu606.vouchermanager.domain.price.Price;
import java.util.UUID;
import lombok.ToString;

public class FixedAmountVoucher extends Voucher {

    public FixedAmountVoucher(UUID uuid, FixedAmountValue fixedAmountValue) {
        super(uuid, fixedAmountValue);
    }

    @Override
    public double calculateDiscountedPrice(Price originalPrice) {
        return Math.max(originalPrice.getValue() - discountValue.getValue(), 0);
    }


}
