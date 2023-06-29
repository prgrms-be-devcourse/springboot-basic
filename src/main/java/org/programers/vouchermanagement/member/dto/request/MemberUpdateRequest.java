package org.programers.vouchermanagement.member.dto.request;

import org.programers.vouchermanagement.member.domain.MemberStatus;

import java.util.UUID;

public class MemberUpdateRequest {

    private final UUID id;
    private final MemberStatus status;

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
