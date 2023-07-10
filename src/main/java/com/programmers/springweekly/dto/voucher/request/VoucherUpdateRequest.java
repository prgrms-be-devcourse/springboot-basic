package com.programmers.springweekly.dto.voucher.request;

import com.programmers.springweekly.domain.voucher.VoucherType;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
public class VoucherUpdateRequest {

    private UUID voucherId;
    private long discountAmount;
    private VoucherType voucherType;

    @Builder
    public VoucherUpdateRequest(UUID voucherId, long discountAmount, VoucherType voucherType) {
        this.voucherId = voucherId;
        this.discountAmount = discountAmount;
        this.voucherType = voucherType;
    }
    
}
