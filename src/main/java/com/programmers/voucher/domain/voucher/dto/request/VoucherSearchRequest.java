package com.programmers.voucher.domain.voucher.dto.request;

import com.programmers.voucher.domain.voucher.domain.VoucherType;
import com.programmers.voucher.domain.voucher.dto.request.validation.VoucherSearchTimeValidation;

import java.time.LocalDateTime;

@VoucherSearchTimeValidation
public class VoucherSearchRequest {
    private final VoucherType voucherType;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    public VoucherSearchRequest(VoucherType voucherType, LocalDateTime startTime, LocalDateTime endTime) {
        this.voucherType = voucherType;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }
}
