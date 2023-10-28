package com.weeklyMission.member.domain;


import java.util.UUID;

public record Member(
    String memberId,
    String name,
    String email,
    Integer age
) {

    @Override
    public String toString() {
        return "이름: " + name + " 이메일 : " + email + " 나이: " + age;
    }
}
