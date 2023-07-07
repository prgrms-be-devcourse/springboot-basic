package com.prgrms.model.voucher;

import com.prgrms.model.order.OrderItem;
import com.prgrms.model.voucher.discount.Discount;
import com.prgrms.model.voucher.discount.Price;
import com.prgrms.util.KeyGenerator;

import java.util.UUID;

public abstract class Voucher {
    private final UUID voucherId = KeyGenerator.make();
    private final Discount discount;
    private final VoucherType voucherType;

    public Voucher(Discount discount, VoucherType voucherType) {
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

    abstract public Price discountPrice(OrderItem orderItem);

}
