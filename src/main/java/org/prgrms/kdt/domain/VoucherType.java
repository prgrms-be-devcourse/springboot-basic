package org.prgrms.kdt.domain;

import org.prgrms.kdt.exception.WrongSalePrice;
import org.prgrms.kdt.exception.InvalidVoucherTypeException;

import java.util.HashMap;
import java.util.Map;
import java.util.function.DoubleBinaryOperator;

public enum VoucherType {
    FIXED_AMOUNT_VOUCHER("fixed", (price, discountAmount) -> {
        double salePrice = price - discountAmount;
        if (salePrice < 0) throw new WrongSalePrice();
        return salePrice;
    }),
    PERCENT_DISCOUNT_VOUCHER("percent", (price, discountAmount) -> {
        double salePrice = price - ((price * discountAmount) / 100);
        if (salePrice < 0) throw new WrongSalePrice();
        return salePrice;
    });

    private final DoubleBinaryOperator discount;

    VoucherType(String code, DoubleBinaryOperator discount) {
        this.discount = discount;
    }

    private static final Map<String, VoucherType> vouchers
            = new HashMap<>() {
        {
            put("fixed", FIXED_AMOUNT_VOUCHER);
            put("percent", PERCENT_DISCOUNT_VOUCHER);
        }
    };

    public double getSalePrice(double price, double discountAmount) {
        return discount.applyAsDouble(price, discountAmount);
    }

    public static VoucherType getVoucherTypeByCode(String code) {
        if (!vouchers.containsKey(code)) throw new InvalidVoucherTypeException();
        return vouchers.get(code);
    }
}
