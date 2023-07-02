package kr.co.springbootweeklymission.vouchermember.domain.model;

import lombok.*;

import java.util.UUID;

public class VoucherMemberReqDTO {
    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CREATE {
        private UUID voucherId;
        private UUID memberId;
    }
}
