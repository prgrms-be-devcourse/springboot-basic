package devcourse.springbootbasic.dto.voucher;


import devcourse.springbootbasic.domain.voucher.Voucher;

public class VoucherFindResponse {

    private final String id;
    private final String voucherType;
    private final long discountValue;
    private final String customerId;

    public VoucherFindResponse(Voucher voucher) {
        this.id = voucher.getId().toString();
        this.voucherType = voucher.getVoucherType().name();
        this.discountValue = voucher.getDiscountValue();
        this.customerId = voucher.getCustomerId() == null
                ? "Not assigned"
                : voucher.getCustomerId().toString();
    }

    @Override
    public String toString() {
        return """
                id = %s
                voucherType = %s
                discountValue = %d
                customerId = %s
                """.formatted(id, voucherType, discountValue, customerId);
    }
}
