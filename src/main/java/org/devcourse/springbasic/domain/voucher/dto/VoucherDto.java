package org.devcourse.springbasic.domain.voucher.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.devcourse.springbasic.domain.voucher.domain.VoucherType;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.UUID;

public class VoucherDto {

    @AllArgsConstructor
    @Getter
    public static class SaveRequest {
        private final VoucherType voucherType;
        private final Long discountRate;
    }

    @AllArgsConstructor
    @Getter
    public static class Response {
        private final UUID voucherId;
        private final String voucherType;
        private final String discountRate;
    }

    @AllArgsConstructor
    @Getter
    public static class Request{
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private final LocalDate creationStartDate;
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private final LocalDate creationEndDate;
        private final String discountType;
    }
}
