package com.prgrms.model.voucher;

import com.prgrms.model.order.OrderItem;
import com.prgrms.model.voucher.discount.Discount;
import com.prgrms.model.voucher.discount.Price;

public class PercentDiscountVoucher extends Voucher {
    private final double PERCENT = 100;

    public PercentDiscountVoucher(Discount discount, VoucherType voucherType) {
        super(discount, voucherType);
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
