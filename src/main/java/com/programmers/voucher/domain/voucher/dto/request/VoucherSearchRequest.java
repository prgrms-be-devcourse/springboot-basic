package com.programmers.voucher.domain.voucher.dto.request;

import com.programmers.voucher.domain.voucher.domain.VoucherType;

import java.time.LocalDateTime;

public class VoucherSearchRequest {
    private VoucherType voucherType;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public VoucherSearchRequest() {
    }

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

    public void setVoucherType(VoucherType voucherType) {
        this.voucherType = voucherType;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}
