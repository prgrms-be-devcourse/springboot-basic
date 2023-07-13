package kr.co.springbootweeklymission.voucher.api.dto.request;

import kr.co.springbootweeklymission.voucher.domain.model.VoucherPolicy;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VoucherReqDTO {
    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CREATE {
        private int amount;
        private VoucherPolicy voucherPolicy;
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
