package kr.co.springbootweeklymission.domain.voucher.api.request;

import kr.co.springbootweeklymission.domain.voucher.entity.model.VoucherPolicy;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
}
