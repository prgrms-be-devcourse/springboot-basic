package com.weeklyMission.member.domain;


import java.util.UUID;

public record Member(
    UUID memberId,
    String name,
    Integer age,
    boolean blackList
) {

    @Override
    public String toString() {
        return "이름: " + name + " 나이: " + age + " 밴 여부: " + blackList;
    }
}
