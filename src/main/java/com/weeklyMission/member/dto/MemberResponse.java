package com.weeklyMission.member.dto;

import com.weeklyMission.member.domain.Member;
import java.util.UUID;

public record MemberResponse(
    UUID id,
    String name,
    String email,
    Integer age
) {

    public static MemberResponse of(Member member){
        return new MemberResponse(member.memberId(), member.name(), member.email(), member.age());
    }

    @Override
    public String toString() {
        return "MemberId : " + id + " Name : " + name + " Email : " + email + " age : " + age;
    }
}
