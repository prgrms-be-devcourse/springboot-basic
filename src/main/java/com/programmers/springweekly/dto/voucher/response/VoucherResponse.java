package com.programmers.springweekly.dto.voucher.response;

import com.programmers.springweekly.domain.voucher.Voucher;
import com.programmers.springweekly.domain.voucher.VoucherType;
import java.util.UUID;
import lombok.Getter;

@Getter
public class VoucherResponse {

    private UUID voucherId;
    private long discountAmount;
    private VoucherType voucherType;

    public VoucherResponse(Voucher voucher) {
        this.voucherId = voucher.getVoucherId();
        this.discountAmount = voucher.getVoucherAmount();
        this.voucherType = voucher.getVoucherType();
    }
    
}
