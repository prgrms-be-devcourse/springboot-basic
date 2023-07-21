package com.prgrms.voucher.model;

import com.prgrms.order.model.OrderItem;
import com.prgrms.order.model.Price;
import com.prgrms.voucher.model.discount.Discount;

public abstract class Voucher {

    private final int voucherId;
    private final Discount discount;
    private final VoucherType voucherType;

    public Voucher(int voucherId, Discount discount, VoucherType voucherType) {
        this.voucherId = voucherId;
        this.discount = discount;
        this.voucherType = voucherType;
    }

    public int getVoucherId() {
        return voucherId;
    }

    public Discount getVoucherDiscount() {
        return discount;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public Price discountPrice(OrderItem orderItem) {
        Price originalPrice = orderItem.productPrice();
        return getVoucherDiscount().sale(originalPrice);
    }

}
