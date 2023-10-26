package com.weeklyMission.member.domain;


public record Member(
    Long memberId,
    String name,
    Integer age,
    String reason
) {

    @Override
    public String toString() {
        return "이름: " + name + " 나이: " + age + " 사유: " + reason;
    }
}
