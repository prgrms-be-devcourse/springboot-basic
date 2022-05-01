package com.waterfogsw.voucher.voucher.domain;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.time.LocalDateTime;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type",
        include = JsonTypeInfo.As.EXISTING_PROPERTY
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = FixedAmountVoucher.class, name = "FIXED_AMOUNT"),
        @JsonSubTypes.Type(value = PercentDiscountVoucher.class, name = "PERCENT_DISCOUNT"),
})
public abstract class Voucher {

    private final Long id;
    private final VoucherType type;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    protected Voucher(Long id, VoucherType type) {
        this(id, type, LocalDateTime.now(), LocalDateTime.now());
    }

    protected Voucher(Long id, VoucherType type, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.type = type;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public VoucherType getType() {
        return type;
    }

    public static Voucher of(Long id, VoucherType type, int value, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return switch (type) {
            case FIXED_AMOUNT -> new FixedAmountVoucher(id, value, createdAt, updatedAt);
            case PERCENT_DISCOUNT -> new PercentDiscountVoucher(id, value, createdAt, updatedAt);
        };
    }

    public abstract int getValue();

    public abstract int discount(int before);

    protected abstract void validate(int value);

    public static Voucher of(VoucherType type, int value) {
        return switch (type) {
            case FIXED_AMOUNT -> new FixedAmountVoucher(value);
            case PERCENT_DISCOUNT -> new PercentDiscountVoucher(value);
        };
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public static Voucher toEntity(Long id, Voucher domain) {
        return switch (domain.getType()) {
            case FIXED_AMOUNT -> new FixedAmountVoucher(id, domain.getValue());
            case PERCENT_DISCOUNT -> new PercentDiscountVoucher(id, domain.getValue());
        };
    }
}
