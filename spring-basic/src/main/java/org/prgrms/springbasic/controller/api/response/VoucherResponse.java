package org.prgrms.springbasic.controller.api.response;

import lombok.Getter;
import org.prgrms.springbasic.domain.voucher.Voucher;
import org.prgrms.springbasic.domain.voucher.VoucherType;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class VoucherResponse {

    private final UUID voucherId;
    private final VoucherType voucherType;
    private final long discountInfo;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final UUID customerId;

    private VoucherResponse(Voucher voucher) {
        this.voucherId = voucher.getVoucherId();
        this.voucherType = voucher.getVoucherType();
        this.discountInfo = voucher.getDiscountInfo();
        this.createdAt = voucher.getCreatedAt();
        this.modifiedAt = voucher.getModifiedAt();
        this.customerId = voucher.getCustomerId();
    }

    public static VoucherResponse of(Voucher voucher) {
        return new VoucherResponse(voucher);
    }
}