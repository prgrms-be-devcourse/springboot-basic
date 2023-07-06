package kr.co.springbootweeklymission.voucher.presentation.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import kr.co.springbootweeklymission.voucher.domain.model.VoucherPolicy;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VoucherReqDTO {
    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CREATE {
        @Min(value = 1, message = "할인량을 입력해주세요.")
        private int amount;
        @NotBlank(message = "할인 정책을 입력해주세요.")
        private String voucherPolicy;
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class UPDATE {
        private int amount;
        private VoucherPolicy voucherPolicy;
    }
}
