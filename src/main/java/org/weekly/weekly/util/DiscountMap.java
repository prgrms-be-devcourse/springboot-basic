package org.weekly.weekly.util;

import org.weekly.weekly.voucher.domain.Discount;
import org.weekly.weekly.voucher.domain.FixedDiscount;
import org.weekly.weekly.voucher.domain.PercentDiscount;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum DiscountMap {
    FIXED("1", FixedDiscount.class, "1. Fixed Discount"),
    PERCENT("2", PercentDiscount.class, "2. Percent Discount");

    private String no;
    private Class<? extends Discount> cls;
    private String msg;

    private static final Map<String, DiscountMap> discuontMap;
    static {
        Map<String, DiscountMap> map = new HashMap<>();
        for (DiscountMap discount : DiscountMap.values()) {
            map.put(discount.no, discount);
        }
        discuontMap = Collections.unmodifiableMap(map);
    }

    DiscountMap(String no, Class<? extends Discount> cls, String msg) {
        this.no = no;
        this.cls = cls;
        this.msg = msg;
    }

    public static DiscountMap getDiscountMap(String no) {
        if (discuontMap.containsKey(no)) {
            return discuontMap.get(no);
        }
        throw new RuntimeException(ExceptionMsg.NOT_DISCOUNT.getMsg());
    }

    public Class<? extends Discount> getCls() {
        return cls;
    }

    public String getMsg() {
        return msg;
    }
}
