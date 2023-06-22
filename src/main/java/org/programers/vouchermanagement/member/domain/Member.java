package org.programers.vouchermanagement.member.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class Member {

    private final UUID id;

    private MemberStatus status;

    @Autowired
    public Member() {
        this(UUID.randomUUID(), MemberStatus.NORMAL);
    }

    public Member(UUID id, MemberStatus status) {
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
