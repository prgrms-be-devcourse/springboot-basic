package kr.co.springbootweeklymission.domain.voucher.api.response;

import kr.co.springbootweeklymission.domain.voucher.entity.model.VoucherPolicy;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VoucherResDTO {
    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class READ {
        private UUID voucherId;
        private int amount;
        private VoucherPolicy voucherPolicy;
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class FILE {
        private UUID voucherId;
        private int amount;
        private VoucherPolicy voucherPolicy;
    }
}
