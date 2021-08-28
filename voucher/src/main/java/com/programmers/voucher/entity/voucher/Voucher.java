package com.programmers.voucher.entity.voucher;

import java.io.Serializable;
import java.time.LocalDate;

public class Voucher implements Serializable {

    private long id;
    private String name;
    private DiscountPolicy discountPolicy;
    private LocalDate createdAt;
    private long customerId;

    public Voucher(long id, String name, DiscountPolicy discountPolicy, LocalDate createdAt, long customerId) {
        this.id = id;
        this.name = name;
        this.discountPolicy = discountPolicy;
        this.createdAt = createdAt;
        this.customerId = customerId;
    }

    public Voucher(String name, DiscountPolicy discountPolicy, long customerId) {
        this.name = name;
        this.discountPolicy = discountPolicy;
        this.customerId = customerId;
        this.createdAt = LocalDate.now();
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

    public DiscountPolicy getDiscountPolicy() {
        return discountPolicy;
    }

    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
        this.discountPolicy = discountPolicy;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public int discount(int original) {
        return discountPolicy.discount(original);
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return String.format("[Voucher #%d] %s - %s %d", id, name, discountPolicy.getType().toString(), discountPolicy.getAmount());
    }

}
