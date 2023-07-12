package org.devcourse.springbasic.domain.voucher.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.devcourse.springbasic.domain.voucher.domain.VoucherType;

import java.util.UUID;

public class VoucherDto {

    @AllArgsConstructor
    @Getter
    @Builder
    public static class SaveRequestDto {
        private final VoucherType voucherType;
    }

    @AllArgsConstructor
    @Getter
    public static class ResponseDto {
        private final UUID voucherId;
        private final VoucherType voucherType;
        private final long discountRate;
    }

    @AllArgsConstructor
    @Getter
    @Builder
    public static class HistoryRequestDto {

    }

    @AllArgsConstructor
    @Getter
    @Builder
    public static class HistoryResponseDto {

    }


}
