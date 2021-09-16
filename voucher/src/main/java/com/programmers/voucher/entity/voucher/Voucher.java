package com.programmers.voucher.entity.voucher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Voucher implements Serializable {

    private Long id;
    private String name;
    private DiscountPolicy discountPolicy;
    private final LocalDate createdAt;
    private long customerId;

    private static final Logger log = LoggerFactory.getLogger(Voucher.class);

    public enum SearchCriteria {
        VOUCHER_TYPE("voucher_type", (repository, from, to, value) ->
                repository.listAllBetweenByVoucherType(from, to, DiscountType.of(value))),
        UNKNOWN("unknown", (repository, from, to, value) ->
                repository.listAllBetween(from, to));

        SearchCriteria(String name, DateBasedVoucherSearch search) {
            this.name = name;
            this.search = search;
        }

        String name;
        DateBasedVoucherSearch search;

        public String getName() {
            return name;
        }

        public DateBasedVoucherSearch getSearch() {
            return search;
        }

        public static SearchCriteria of(String name) {
            try {
                return SearchCriteria.valueOf(name.toUpperCase());
            } catch (IllegalArgumentException ex) {
                return SearchCriteria.UNKNOWN;
            }
        }
    }

    public Voucher(Long id, String name, DiscountPolicy discountPolicy, LocalDate createdAt, long customerId) {
        this.id = id;
        this.name = name;
        this.discountPolicy = discountPolicy;
        this.createdAt = createdAt;
        this.customerId = customerId;
    }

    public Voucher(String name, DiscountPolicy discountPolicy, long customerId) {
        this(null, name, discountPolicy, LocalDate.now(), customerId);
    }

    public long getId() {
        return id;
    }

    public void registerId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public DiscountPolicy getDiscountPolicy() {
        return discountPolicy;
    }

    public void replaceDiscountPolicy(DiscountPolicy discountPolicy) {
        this.discountPolicy = discountPolicy;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void updateCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public int discount(int original) {
        return discountPolicy.discount(original);
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void update(UpdatableField field, String value) {
        log.debug("Updating voucher({})'s field({}) to {}", name, field, value);
        field.update(this, value);
    }

    @Override
    public String toString() {
        return String.format("[Voucher #%d] %s / %s %d / Owner Id: %d", id, name, discountPolicy.getType().toString(), discountPolicy.getAmount(), customerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, discountPolicy, createdAt, customerId);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Voucher)) return false;
        Voucher other = (Voucher) obj;
        return (this.id == null || id.equals(other.id)) &&
                this.discountPolicy.equals(other.discountPolicy) &&
                this.name.equals(other.name) &&
                this.customerId == other.customerId &&
                this.createdAt.equals(other.createdAt);
    }
}
