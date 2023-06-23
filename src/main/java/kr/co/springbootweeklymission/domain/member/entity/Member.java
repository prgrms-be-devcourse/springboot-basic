package kr.co.springbootweeklymission.domain.member.entity;

import kr.co.springbootweeklymission.domain.member.dto.response.MemberResDTO;
import kr.co.springbootweeklymission.domain.model.MemberStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
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
