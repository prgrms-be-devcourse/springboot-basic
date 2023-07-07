package kr.co.springbootweeklymission.member.domain.entity;

import kr.co.springbootweeklymission.member.api.dto.response.MemberResDTO;
import kr.co.springbootweeklymission.member.domain.model.MemberStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Member {
    private UUID memberId;
    private MemberStatus memberStatus;

    @Builder
    private Member(UUID memberId,
                   MemberStatus memberStatus) {
        this.memberId = memberId;
        this.memberStatus = memberStatus;
    }

    public static MemberResDTO.READ toMemberReadDto(Member member) {
        return MemberResDTO.READ.builder()
                .memberId(member.memberId)
                .memberStatus(member.memberStatus)
                .build();
    }

    public boolean isBlackMember() {
        return this.memberStatus == MemberStatus.BLACK;
    }
}
