package org.programers.vouchermanagement.member.dto;

import org.programers.vouchermanagement.member.domain.MemberStatus;

import java.util.UUID;

public class MemberUpdateRequest {

    private UUID id;
    private MemberStatus status;

    public MemberUpdateRequest(UUID id, MemberStatus status) {
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
