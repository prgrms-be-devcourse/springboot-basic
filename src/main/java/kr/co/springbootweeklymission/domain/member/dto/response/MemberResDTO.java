package kr.co.springbootweeklymission.domain.member.dto.response;

import kr.co.springbootweeklymission.domain.model.MemberStatus;
import lombok.*;

import java.util.UUID;

public class MemberResDTO {

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class READ {
        private UUID memberId;
        private MemberStatus memberStatus;
    }
}
