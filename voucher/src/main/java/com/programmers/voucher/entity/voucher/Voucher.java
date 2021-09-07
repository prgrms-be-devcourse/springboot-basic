package com.programmers.voucher.entity.voucher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.function.BiConsumer;

public class Voucher implements Serializable {

    private long id;
    private String name;
    private DiscountPolicy discountPolicy;
    private LocalDate createdAt;
    private long customerId;
    private static final Logger log = LoggerFactory.getLogger(Voucher.class);

    public enum  UpdatableField {
        NAME("name", (voucher, input) -> {
            if(input.isBlank()) {
                log.info("Voucher name cannot be empty.");
            } else {
                log.debug("Updating voucher name from {} to {}", voucher.getName(), input);
                voucher.setName(input);
                log.debug("Updated voucher name to {}", input);
            }
        }),
        TYPE("type", (voucher, input) -> {
            final DiscountPolicy.Type newType = DiscountPolicy.Type.of(input);
            if(newType.equals(DiscountPolicy.Type.UNKNOWN)) {
                log.info("Invalid voucher type. Please check your input.");
            } else {
                log.debug("Updating voucher type from {} to {}", voucher.getDiscountPolicy().getType(), newType);
                voucher.getDiscountPolicy().setType(newType);
                log.debug("Updated voucher type to {}", newType);
            }
        }),
        VALUE("value", (voucher, input) -> {
            try {
                final int newValue = Integer.parseInt(input);
                log.debug("Updating voucher value from {} to {}", voucher.getDiscountPolicy().getAmount(), newValue);
                voucher.getDiscountPolicy().setAmount(newValue);
                log.debug("Updated voucher value to {}", newValue);
            } catch (NumberFormatException ex) {
                log.info("Invalid number format. Please check your input.");
            }
        }),
        CUSTOMER("customer", (voucher, input) -> {
            try {
                final long newCustomer = Long.parseLong(input);
                log.debug("Updating customer id from {} to {}", voucher.getCustomerId(), newCustomer);
                voucher.setCustomerId(newCustomer);
                log.debug("Updated customer id to {}", newCustomer);
            } catch (NumberFormatException ex) {
                log.info("Invalid number format. Please check your input.");
            }
        }),
        UNKNOWN("unknown", (voucher, input) -> {throw new UnsupportedOperationException("Unknown updating field.");});

        String name;
        BiConsumer<Voucher, String> behavior;

        UpdatableField(String name, BiConsumer<Voucher, String> behavior) {
            this.name = name;
            this.behavior = behavior;
        }

        public static UpdatableField of(String input) {
            try {
                return UpdatableField.valueOf(input.toUpperCase());
            } catch (IllegalArgumentException ex) {
                return UNKNOWN;
            }
        }
    }

    public enum SearchCriteria {
        VOUCHER_TYPE("voucher_type", (repository, from, to, value) ->
                repository.listAllBetweenByVoucherType(from, to, DiscountPolicy.Type.of(value))),
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

    public void update(UpdatableField field, String value) {
        field.behavior.accept(this, value);
    }

    @Override
    public String toString() {
        return String.format("[Voucher #%d] %s / %s %d / Owner Id: %d", id, name, discountPolicy.getType().toString(), discountPolicy.getAmount(), customerId);
    }

    @Override
    public int hashCode() {
        return (int) this.id;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Voucher)) return false;
        Voucher other = (Voucher) obj;
        return this.id == other.id &&
                this.discountPolicy.equals(other.discountPolicy) &&
                this.name.equals(other.name) &&
                this.customerId == other.customerId &&
                this.createdAt.equals(other.createdAt);
    }
}
