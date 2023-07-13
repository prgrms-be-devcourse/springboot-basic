package programmers.org.voucher.dto;

import programmers.org.voucher.domain.Voucher;

public class VoucherResponse {

    private Long id;

    private int discountAmount;

    private String type;

    public VoucherResponse(Voucher voucher) {
        this.id = voucher.getId();
        this.discountAmount = voucher.getDiscountAmount();
        this.type = voucher.getType().toString();
    }

    public Long getId() {
        return id;
    }

    public int getDiscountAmount() {
        return discountAmount;
    }

    public String getType() {
        return type;
    }
}
