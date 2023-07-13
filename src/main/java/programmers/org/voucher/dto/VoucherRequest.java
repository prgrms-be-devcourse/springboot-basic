package programmers.org.voucher.dto;

import programmers.org.voucher.constant.VoucherType;
import programmers.org.voucher.domain.Voucher;

public class VoucherRequest {

    private int discountAmount;

    private String type;

    public VoucherRequest(int discountAmount, String type) {
        this.discountAmount = discountAmount;
        this.type = type;
    }

    public int getDiscountAmount() {
        return discountAmount;
    }

    public Voucher toEntity() {
        return new Voucher(discountAmount, VoucherType.find(type));
    }
}
