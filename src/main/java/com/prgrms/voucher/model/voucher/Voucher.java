package com.prgrms.voucher.model.voucher;

import com.prgrms.common.util.Generator;
import com.prgrms.order.model.OrderItem;
import com.prgrms.order.model.Price;
import com.prgrms.voucher.model.VoucherType;
import com.prgrms.voucher.model.discount.Discount;
import java.time.LocalDateTime;

public abstract class Voucher {

    private final String voucherId;
    private final Discount discount;
    private final VoucherType voucherType;
    private final LocalDateTime createdAt;
    private final boolean deleted = false;

    public Voucher(Generator generator, Discount discount, VoucherType voucherType) {
        this.voucherId = generator.makeKey();
        this.createdAt = generator.makeDate();
        this.discount = discount;
        this.voucherType = voucherType;
    }

    public Voucher(String voucherId, Discount discount, VoucherType voucherType,
            LocalDateTime createdAt) {
        this.voucherId = voucherId;
        this.discount = discount;
        this.voucherType = voucherType;
        this.createdAt = createdAt;
    }

    public String getVoucherId() {
        return voucherId;
    }

    public Discount getVoucherDiscount() {
        return discount;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Price discountPrice(OrderItem orderItem) {
        Price originalPrice = orderItem.productPrice();
        return getVoucherDiscount().sale(originalPrice);
    }

}
