package com.programmers.part1.member.entity;

import lombok.Getter;

@Getter
public class MemberDto {
    private long id;
    private String name;
    private String memberType;

    public MemberDto(long id, String name, String memberType) {
        this.id = id;
        this.name = name;
        this.memberType = memberType;
    }
}
