package com.weeklyMission.member.dto;

import com.weeklyMission.member.domain.Member;
import java.util.UUID;

public record MemberRequest(
    String name,
    String email,
    Integer age
) {
    public Member toEntity(){
        return new Member(UUID.randomUUID().toString(), name, email, age);
    }
}
