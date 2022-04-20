package com.manager.voucher.domain;


import java.time.LocalDateTime;
import java.util.UUID;

public class PercentDiscountVoucher implements Voucher{
    private int discountPercent;
    private UUID voucherId;
    private LocalDateTime expireDate;
    private boolean isUsed;

    public PercentDiscountVoucher(int discountPercent, LocalDateTime expireDate) {
        this.discountPercent = discountPercent;
        this.expireDate = expireDate;
        this.isUsed = false;
    }

    @Override
    public void discountProduct(Product product) {
        if(!isUsed)  product.discountPriceByPercent(discountPercent);
    }

    @Override
    public void used() {
        this.isUsed = true;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("voucher id : "+this.voucherId + "\n");
        sb.append("percent discount: " + discountPercent+"\n");
        return sb.toString();
    }
}
