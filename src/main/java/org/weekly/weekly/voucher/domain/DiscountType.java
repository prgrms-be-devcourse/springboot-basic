package org.weekly.weekly.voucher.domain;

import org.weekly.weekly.util.ExceptionMsg;
import org.weekly.weekly.voucher.exception.VoucherException;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum DiscountType {
    FIXED("1", FixedDiscount.class, "1. Fixed Discount", "바우처 할인 금액, 바우처 유효 개월 수"),
    PERCENT("2", PercentDiscount.class, "2. Percent Discount", "0~100사이의 바우처 할인율, 바우처 유효 개월 수");

    private final String no;
    private final Class<? extends Discount> cls;
    private final String selectMessage;
    private final String inputExampleMessage;

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
        throw new VoucherException(ExceptionMsg.NOT_DISCOUNT);
    }

    public static DiscountType getDiscountTypeByName(String name) {
        for (DiscountType discount : DiscountType.values()) {
            if (name.equals(discount.name())) {
                return discount;
            }
        }
        throw new VoucherException(ExceptionMsg.NOT_DISCOUNT);
    }

    public Discount getNewInstance() {
        try {
            return this.cls.getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException exception) {
            throw new VoucherException(ExceptionMsg.NOT_FOUND);
        }

    }


    public String getSelectMessage() {
        return selectMessage;
    }

    public String getInputExampleMessage() {
        return inputExampleMessage;
    }
}
