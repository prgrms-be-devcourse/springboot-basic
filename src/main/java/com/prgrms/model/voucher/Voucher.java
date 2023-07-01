package com.prgrms.model.voucher;

import com.prgrms.model.order.OrderItem;

import java.util.UUID;

public abstract class Voucher {
    public UUID voucherId;
    public Discount discount;
    public VoucherType voucherType;

    public Voucher(UUID voucherId, Discount discount, VoucherType voucherType) {
        this.voucherId = voucherId;
        this.discount = discount;
        this.voucherType = voucherType;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public Discount getVoucherDiscount() {
        return discount;
    }

    public VoucherType getVoucherPolicy() {
        return voucherType;
    }

    public double getRealPrice(OrderItem orderItem) {
        return orderItem.productPrice() - sale(orderItem.productPrice());
    }

    abstract public double sale(long price);

}
