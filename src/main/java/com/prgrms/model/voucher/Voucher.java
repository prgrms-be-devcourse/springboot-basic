package com.prgrms.model.voucher;

import com.prgrms.model.order.OrderItem;
import com.prgrms.model.voucher.dto.discount.Discount;
import com.prgrms.model.voucher.dto.price.Price;

public abstract class Voucher {
    private final int voucherId ;
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

    abstract public Price discountPrice(OrderItem orderItem);

}
