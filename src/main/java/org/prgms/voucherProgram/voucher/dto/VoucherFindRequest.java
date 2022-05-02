package org.prgms.voucherProgram.voucher.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Objects;

import org.prgms.voucherProgram.voucher.domain.VoucherType;

public class VoucherFindRequest {
    private final Integer type;
    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;

    public VoucherFindRequest(Integer type, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.type = type;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public static VoucherFindRequest of(Map<String, String> params) {
        try {
            Integer type = parseVoucherTypeNumber(params);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
            LocalDateTime startDateTime = parseStartDateTime(params, formatter);
            LocalDateTime endDateTime = parseEndDateTime(params, formatter);
            return new VoucherFindRequest(type, startDateTime, endDateTime);
        } catch (Exception e) {
            throw new IllegalArgumentException("알맞은 형식이 아닙니다.");
        }
    }

    private static Integer parseVoucherTypeNumber(Map<String, String> params) {
        if (params.get("type") != null) {
            return VoucherType.valueOf(params.get("type")).getNumber();
        }
        return null;
    }

    private static LocalDateTime parseStartDateTime(Map<String, String> params, DateTimeFormatter formatter) {
        if (params.get("start") != null) {
            return LocalDateTime.parse(params.get("start"), formatter);
        }
        return null;
    }

    private static LocalDateTime parseEndDateTime(Map<String, String> params, DateTimeFormatter formatter) {
        if (params.get("end") != null) {
            return LocalDateTime.parse(params.get("end"), formatter);
        }
        return null;
    }

    public boolean isTypeNull() {
        return Objects.isNull(type);
    }

    public Integer getType() {
        return type;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }
}
