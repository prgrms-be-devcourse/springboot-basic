package com.zerozae.voucher.dto.voucher;

import com.zerozae.voucher.domain.voucher.UseStatusType;
import lombok.Getter;

@Getter
public class VoucherUpdateRequest {
    private long discount;
    private UseStatusType useStatusType;

    public VoucherUpdateRequest(long discount, UseStatusType useStatusType) {
        this.discount = discount;
        this.useStatusType = useStatusType;
    }
}
