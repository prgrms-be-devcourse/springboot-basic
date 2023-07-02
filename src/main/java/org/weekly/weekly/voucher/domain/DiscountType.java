package org.weekly.weekly.voucher.domain;

import org.weekly.weekly.util.ExceptionMsg;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum DiscountType {
    FIXED("1", FixedDiscount.class, "1. Fixed Discount", "바우처 할인 금액, 바우처 유효 개월 수"),
    PERCENT("2", PercentDiscount.class, "2. Percent Discount", "0~100사이의 바우처 할인율, 바우처 유효 개월 수");

    private String no;
    private Class<? extends Discount> cls;
    private String selectMessage;
    private String inputExampleMessage;

    private static final Map<String, DiscountType> discuontTypeMap;

    static {
        Map<String, DiscountType> map = new HashMap<>();
        for (DiscountType discount : DiscountType.values()) {
            map.put(discount.no, discount);
        }
        discuontTypeMap = Collections.unmodifiableMap(map);
    }

    DiscountType(String no, Class<? extends Discount> cls, String msg, String exMessage) {
        this.no = no;
        this.cls = cls;
        this.selectMessage = msg;
        this.inputExampleMessage = exMessage;
    }

    public static DiscountType getDiscountTypeByNumber(String no) {
        if (discuontTypeMap.containsKey(no)) {
            return discuontTypeMap.get(no);
        }
        throw new RuntimeException(ExceptionMsg.NOT_DISCOUNT.getMsg());
    }

    public Discount getNewInstance() throws Exception {
        return this.cls.getDeclaredConstructor().newInstance();
    }

    public Class<? extends Discount> getCls() {
        return cls;
    }

    public String getSelectMessage() {
        return selectMessage;
    }

    public String getInputExampleMessage() {
        return inputExampleMessage;
    }
}
