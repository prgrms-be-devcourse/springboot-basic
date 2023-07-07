package com.prgrms.model.voucher;

import com.prgrms.model.order.OrderItem;
import com.prgrms.model.voucher.discount.Discount;
import com.prgrms.model.voucher.discount.Price;

public class FixedAmountVoucher extends Voucher {

    public FixedAmountVoucher(Discount discount, VoucherType voucherType) {
        super(discount, voucherType);
    }

    @Override
    public Price discountPrice(OrderItem orderItem) {
        var originalPrice = orderItem.productPrice();
        return new Price(originalPrice - sale());
    }

    public double sale() {
        return getVoucherDiscount().getValue();
    }

}
