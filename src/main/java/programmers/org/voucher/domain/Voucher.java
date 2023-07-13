package programmers.org.voucher.domain;

import programmers.org.voucher.constant.VoucherType;

public class Voucher {
    private Long id;

    private int discountAmount;

    private final VoucherType type;

    public Voucher(int discountAmount, VoucherType type) {
        this.discountAmount = discountAmount;
        this.type = type;
    }

    public Voucher(Long id, int discountAmount, VoucherType type) {
        this.id = id;
        this.discountAmount = discountAmount;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public int getDiscountAmount() {
        return discountAmount;
    }

    public VoucherType getType() {
        return type;
    }
}
