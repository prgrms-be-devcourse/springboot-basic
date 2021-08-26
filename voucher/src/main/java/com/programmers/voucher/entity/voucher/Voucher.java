package com.programmers.voucher.entity.voucher;

import java.io.Serializable;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Voucher implements Serializable {

    private long id;
    private String name;
    private DiscountPolicy discountPolicy;

    public Voucher(String name, DiscountPolicy discountPolicy) {
        this.name = name;
        this.discountPolicy = discountPolicy;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int discount(int original) {
        return discountPolicy.discount(original);
    }

    public DiscountPolicy getDiscountPolicy() {
        return discountPolicy;
    }

    @Override
    public String toString() {
        return String.format("[Voucher #%d] %s - %s %d", id, name, discountPolicy.getType().toString(), discountPolicy.getAmount());
    }

}
