package org.prgrms.kdt.kdtspringorder.voucher.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.prgrms.kdt.kdtspringorder.common.enums.VoucherType;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;


public class VoucherDto {

    @Data
    @Builder
    @RequiredArgsConstructor
    public static class Create {

        @NotNull(message = "바우처 타입은 필수 입력값 입니다.")
        private final VoucherType voucherType;

        @Min(value = 0, message = "적어도 0원 이상을 입력해주세요.")
        @NotNull(message = "할인률 또는 할인가격은 필수 입력값 입니다.")
        private final long amount;

    }

    @Data
    @Builder
    @RequiredArgsConstructor
    public static class Allocate {

        @NotNull(message = "바우처 ID는 필수 입력값 입니다.")
        private final UUID voucherId;

        @NotNull(message = "고객 ID는 필수 입력값 입니다.")
        private final UUID customerId;

    }

}
