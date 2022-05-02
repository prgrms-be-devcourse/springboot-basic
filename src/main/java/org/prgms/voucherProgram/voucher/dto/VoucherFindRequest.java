package org.prgms.voucherProgram.voucher.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.prgms.voucherProgram.voucher.domain.VoucherType;

public class VoucherFindRequest {
    private final int type;
    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;

    public VoucherFindRequest(int type, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.type = type;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
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

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }
}
