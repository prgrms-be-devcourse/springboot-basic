package kr.co.springbootweeklymission.member.presentation.dto.request;

import lombok.*;

public class MemberReqDTO {
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CREATE {
        private String memberStatus;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class UPDATE {
        private String memberStatus;
    }
}
