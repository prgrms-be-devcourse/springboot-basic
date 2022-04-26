package org.prgms.voucherProgram.domain.voucher.dto;

import java.time.LocalDateTime;

public class VoucherFindRequest {
    private final int type;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    public VoucherFindRequest(int type, LocalDateTime startTime, LocalDateTime endTime) {
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getType() {
        return type;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }
}
