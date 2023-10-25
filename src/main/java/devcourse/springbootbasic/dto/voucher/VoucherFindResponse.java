package devcourse.springbootbasic.dto.voucher;


import devcourse.springbootbasic.domain.voucher.Voucher;

public class VoucherFindResponse {

    private final String id;
    private final String voucherType;
    private final long discountValue;

    public VoucherFindResponse(Voucher voucher) {
        this.id = voucher.getId().toString();
        this.voucherType = voucher.getVoucherType().name();
        this.discountValue = voucher.getDiscountValue();
    }

    @Override
    public String toString() {
        return """
                id = %s
                voucherType = %s
                discountValue = %d
                """.formatted(id, voucherType, discountValue);
    }
}
