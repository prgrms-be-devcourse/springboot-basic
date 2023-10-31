package com.weeklyMission.member.dto;

import com.weeklyMission.common.ResponseInfo;
import com.weeklyMission.member.domain.Member;
import java.util.UUID;

public record MemberResponse(
    String id,
    String name,
    String email,
    Integer age
) implements ResponseInfo {

    public static MemberResponse of(Member member){
        return new MemberResponse(member.memberId(), member.name(), member.email(), member.age());
    }

    @Override
    public String printInfo() {
        return "MemberId : " + id + " Name : " + name + " Email : " + email + " age : " + age;    }
}
