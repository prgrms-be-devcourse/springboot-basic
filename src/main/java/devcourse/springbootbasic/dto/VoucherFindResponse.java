package devcourse.springbootbasic.dto;


import devcourse.springbootbasic.domain.voucher.Voucher;

import java.util.UUID;

public class VoucherFindResponse {

    private final UUID id;
    private final String voucherType;
    private final long discountValue;

    public VoucherFindResponse(Voucher voucher) {
        this.id = voucher.getId();
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
