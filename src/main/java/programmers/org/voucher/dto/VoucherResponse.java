package programmers.org.voucher.dto;

import programmers.org.voucher.domain.constant.VoucherType;

public class VoucherResponse {

    private Long id;

    private int discountAmount;

    private VoucherType type;

    public VoucherResponse(Long id, int discountAmount, VoucherType type) {
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
