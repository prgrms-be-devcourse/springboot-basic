package com.programmers.springbootbasic.domain.voucher.domain.VoucherType;

public interface VoucherType {
    String getVoucherTypeName();
    double getDiscountedPrice(double price, int benefitValue);
}
