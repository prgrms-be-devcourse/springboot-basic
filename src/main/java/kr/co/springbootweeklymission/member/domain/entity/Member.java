package kr.co.springbootweeklymission.member.domain.entity;

import kr.co.springbootweeklymission.member.domain.model.MemberStatus;
import lombok.*;

import java.util.UUID;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Member {
    private UUID memberId;
    private MemberStatus memberStatus;
    private UUID voucherId;

    @Builder
    private Member(UUID memberId,
                   MemberStatus memberStatus) {
        this.memberId = memberId;
        this.memberStatus = memberStatus;
    }

    public boolean isBlackMember() {
        return this.memberStatus == MemberStatus.BLACK;
    }
}
