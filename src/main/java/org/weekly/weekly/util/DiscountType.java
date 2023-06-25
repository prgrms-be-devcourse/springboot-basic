package org.weekly.weekly.util;

import org.weekly.weekly.voucher.domain.Discount;
import org.weekly.weekly.voucher.domain.FixedDiscount;
import org.weekly.weekly.voucher.domain.PercentDiscount;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum DiscountType {
    FIXED("1", FixedDiscount.class, "1. Fixed Discount"),
    PERCENT("2", PercentDiscount.class, "2. Percent Discount");

    private String no;
    private Class<? extends Discount> cls;
    private String msg;

    private static final Map<String, DiscountType> discuontMap;

    static {
        Map<String, DiscountType> map = new HashMap<>();
        for (DiscountType discount : DiscountType.values()) {
            map.put(discount.no, discount);
        }
        discuontMap = Collections.unmodifiableMap(map);
    }

    DiscountType(String no, Class<? extends Discount> cls, String msg) {
        this.no = no;
        this.cls = cls;
        this.msg = msg;
    }

    public static DiscountType getDiscountMap(String no) {
        if (discuontMap.containsKey(no)) {
            return discuontMap.get(no);
        }
        throw new RuntimeException(ExceptionMsg.NOT_DISCOUNT.getMsg());
    }

    public Discount getNewInstance() throws Exception {
        return this.cls.getDeclaredConstructor().newInstance();
    }

    public Class<? extends Discount> getCls() {
        return cls;
    }

    public String getMsg() {
        return msg;
    }
}
