package org.programers.vouchermanagement.member.dto;

import org.programers.vouchermanagement.member.domain.Member;

import java.util.UUID;

public class MemberResponse {

    private final UUID id;
    private final String status;

    public MemberResponse(Member member) {
        this(member.getId(), member.getStatus().name());
    }

    public MemberResponse(UUID id, String status) {
        this.id = id;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }
}
