package com.waterfogsw.voucher.voucher.domain;

public class Voucher {
    Long id;
    VoucherType type;
    int value;

    public Voucher(VoucherType type, int value) {
        validateVoucher(type, value);
        this.type = type;
        this.value = value;
    }

    public Voucher(Long id, VoucherType type, int value) {
        this.id = id;
        this.type = type;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public VoucherType getType() {
        return type;
    }

    public int getValue() {
        return value;
    }

    private void validateVoucher(VoucherType type, int value) {
        switch (type) {
            case FIXED_AMOUNT -> {
                if(value < 0) throw new IllegalArgumentException();
            }
            case PERCENT_DISCOUNT -> {
                if(value < 0 || value > 100) throw new IllegalArgumentException();
            }
        }
    }
}
