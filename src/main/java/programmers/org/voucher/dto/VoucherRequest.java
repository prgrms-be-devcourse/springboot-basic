package programmers.org.voucher.dto;

public class VoucherRequest {

    private int discountAmount;

    private String type;

    public VoucherRequest(int discountAmount, String type) {
        this.discountAmount = discountAmount;
        this.type = type;
    }

    public VoucherRequest(int discountAmount) {
        this.discountAmount = discountAmount;
    }

    public int getDiscountAmount() {
        return discountAmount;
    }

    public String getType() {
        return type;
    }
}
