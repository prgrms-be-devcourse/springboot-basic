package com.waterfogsw.voucher.voucher.domain;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

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

    protected Voucher(Long id, VoucherType type) {
        this.id = id;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public VoucherType getType() {
        return type;
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

    public static Voucher toEntity(Long id, Voucher domain) {
        return switch (domain.getType()) {
            case FIXED_AMOUNT -> new FixedAmountVoucher(id, domain.getValue());
            case PERCENT_DISCOUNT -> new PercentDiscountVoucher(id, domain.getValue());
        };
    }
}
