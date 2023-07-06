package org.programers.vouchermanagement.member.dto.response;

import lombok.NoArgsConstructor;
import org.programers.vouchermanagement.member.domain.Member;
import org.programers.vouchermanagement.member.domain.MemberStatus;

import java.util.UUID;

@NoArgsConstructor
public class MemberResponse {

    private UUID id;
    private MemberStatus status;

    public MemberResponse(Member member) {
        this(member.getId(), member.getStatus());
    }

    public MemberResponse(UUID id, MemberStatus status) {
        this.id = id;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public MemberStatus getStatus() {
        return status;
    }
}
