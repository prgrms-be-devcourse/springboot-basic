package com.weeklyMission.member.domain;


public record Member(
    String memberId,
    String name,
    String email,
    Integer age
) {
}
