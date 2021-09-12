package org.prgrms.kdt.kdtspringorder.voucher.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.prgrms.kdt.kdtspringorder.common.enums.VoucherType;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;


public class VoucherDto {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Create {

        @NotNull(message = "바우처 타입은 필수 입력값 입니다.")
        private VoucherType voucherType;

        @Min(value = 0, message = "적어도 0원 이상을 입력해주세요.")
        @NotNull(message = "할인률 또는 할인가격은 필수 입력값 입니다.")
        private long amount;

    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Allocate {

        @NotNull(message = "바우처 ID는 필수 입력값 입니다.")
        private UUID voucherId;

        @NotNull(message = "고객 ID는 필수 입력값 입니다.")
        private UUID customerId;

    }

}
