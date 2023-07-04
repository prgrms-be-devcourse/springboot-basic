package org.programers.vouchermanagement.member.dto.response;

import org.programers.vouchermanagement.member.domain.Member;
import org.programers.vouchermanagement.member.domain.MemberStatus;

import java.util.UUID;

public class MemberResponse {

    private final UUID id;
    private final MemberStatus status;

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
