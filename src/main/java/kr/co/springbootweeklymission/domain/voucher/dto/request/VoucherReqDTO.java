package kr.co.springbootweeklymission.domain.voucher.dto.request;

import kr.co.springbootweeklymission.domain.model.VoucherPolicy;
import lombok.*;

public class VoucherReqDTO {
    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CREATE {
        private int amount;
        private VoucherPolicy voucherPolicy;
    }
}
