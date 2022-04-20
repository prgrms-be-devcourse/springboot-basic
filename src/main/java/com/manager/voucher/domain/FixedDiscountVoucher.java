package com.manager.voucher.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class FixedDiscountVoucher implements Voucher{
    private Long discountAmount;
    private UUID voucherId;
    private LocalDateTime expireDate;
    private boolean isUsed;

    public FixedDiscountVoucher(Long discountAmount, LocalDateTime expireDate) {
        this.discountAmount = discountAmount;
        this.expireDate = expireDate;
        this.isUsed = false;
    }

    @Override
    public void discountProduct(Product product) {
        if(!isUsed) product.discountPriceByAmount(discountAmount);
    }

    @Override
    public void used() {
        this.isUsed = true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("voucher id : "+this.voucherId + "\n");
        sb.append("percent discount: " + discountAmount+"\n");
        return sb.toString();
    }
}
