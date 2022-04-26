package org.prgms.voucherProgram.domain.voucher.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.prgms.voucherProgram.domain.voucher.domain.VoucherType;

public class VoucherFindRequest {
    private final int type;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    public VoucherFindRequest(int type, LocalDateTime startTime, LocalDateTime endTime) {
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static VoucherFindRequest of(Map<String, String> params) {
        try {
            int type = VoucherType.valueOf(params.get("type")).getNumber();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
            LocalDateTime startTime = LocalDateTime.parse(params.get("start"), formatter);
            LocalDateTime endTime = LocalDateTime.parse(params.get("end"), formatter);
            return new VoucherFindRequest(type, startTime, endTime);
        } catch (Exception e) {
            throw new IllegalArgumentException("알맞은 형식이 아닙니다.");
        }
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
