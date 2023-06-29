package org.programers.vouchermanagement.member.dto.request;

import org.programers.vouchermanagement.member.domain.MemberStatus;

public class MemberCreationRequest {

    private final MemberStatus status;

    public MemberCreationRequest(MemberStatus status) {
        this.status = status;
    }

    public MemberStatus getStatus() {
        return status;
    }
}
