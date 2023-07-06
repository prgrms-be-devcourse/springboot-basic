package com.prgrms.model.voucher;

import com.prgrms.model.order.OrderItem;
import com.prgrms.model.voucher.discount.Discount;

import java.util.UUID;

public abstract class Voucher {
    private final UUID voucherId;
    private final Discount discount;
    private final VoucherType voucherType;

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

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public double getDiscountedPrice(OrderItem orderItem) {
        return orderItem.productPrice() - sale(orderItem.productPrice());
    }

    abstract public double sale(long price);

}
