package com.programmers.part1.member.entity;

import lombok.Getter;

@Getter
public class Member {

    private final Long id;
    private final String name;
    private final String memberType;

    public Member(long id, String name, String memberType) {
        this.id = id;
        this.name = name;
        this.memberType = memberType;
    }

    @Override
    public String toString() {
        return "Member{" +
                "name='" + name + '\'' +
                ", memberType=" + memberType +
                '}';
    }
}
