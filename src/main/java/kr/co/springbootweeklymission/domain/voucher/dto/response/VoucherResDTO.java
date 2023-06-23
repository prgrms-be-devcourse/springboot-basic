package kr.co.springbootweeklymission.domain.voucher.dto.response;

import kr.co.springbootweeklymission.domain.model.VoucherPolicy;
import lombok.*;

import java.util.UUID;

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
