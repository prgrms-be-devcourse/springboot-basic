package kr.co.springbootweeklymission.domain.member.entity;

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
}
