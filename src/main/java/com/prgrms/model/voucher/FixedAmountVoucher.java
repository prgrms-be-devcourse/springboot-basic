package com.prgrms.model.voucher;

import com.prgrms.model.order.OrderItem;
import com.prgrms.model.voucher.discount.Discount;
import com.prgrms.model.voucher.discount.Price;

import java.util.UUID;

public class FixedAmountVoucher extends Voucher {

    public FixedAmountVoucher(UUID voucherId, Discount discount, VoucherType voucherType) {
        super(voucherId, discount, voucherType);
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
