package kr.co.springbootweeklymission.wallet.api.dto.request;

import lombok.*;

import java.util.UUID;

public class WalletReqDTO {
    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CREATE {
        private UUID voucherId;
        private UUID memberId;
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class DELETE {
        private UUID voucherId;
        private UUID memberId;
    }
}
