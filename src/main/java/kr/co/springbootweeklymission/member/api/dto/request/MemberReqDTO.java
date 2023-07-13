package kr.co.springbootweeklymission.member.api.dto.request;

import kr.co.springbootweeklymission.member.domain.model.MemberStatus;
import lombok.*;

public class MemberReqDTO {
    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CREATE {
        private MemberStatus memberStatus;
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class UPDATE {
        private MemberStatus memberStatus;
    }
}
