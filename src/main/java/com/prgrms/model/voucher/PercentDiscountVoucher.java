package com.prgrms.model.voucher;

import com.prgrms.model.order.OrderItem;
import com.prgrms.model.voucher.dto.discount.Discount;
import com.prgrms.model.voucher.dto.price.Price;

public class PercentDiscountVoucher extends Voucher {
    private final double PERCENT = 100;

    public PercentDiscountVoucher(int voucherId, Discount discount, VoucherType voucherType) {
        super(voucherId, discount, voucherType);
    }

    @Override
    public Price discountPrice(OrderItem orderItem) {
        var originalPrice = orderItem.productPrice();
        Price dicountedPrice = new Price(originalPrice - originalPrice * sale());
        return dicountedPrice;
    }

    public double sale() {
        return getVoucherDiscount().getValue() / PERCENT;
    }
}
