package com.prgrms.model.voucher;

import com.prgrms.model.order.OrderItem;
import java.util.UUID;

public abstract class Voucher {
    public UUID voucherId;
    public Discount discount;
    public VoucherPolicy voucherPolicy;

    public Voucher(UUID voucherId, Discount discount, VoucherPolicy voucherPolicy) {
        this.voucherId = voucherId;
        this.discount = discount;
        this.voucherPolicy = voucherPolicy;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public Discount getVoucherDiscount() {
        return discount;
    }

    public VoucherPolicy getVoucherPolicy() {
        return voucherPolicy;
    }

    public double getRealPrice(OrderItem orderItem) {
        return orderItem.productPrice() - sale(orderItem.productPrice());
    }
    abstract public double sale(long price);

}
