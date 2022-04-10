package com.pppp0722.vouchermanagement.member.repository;

import com.pppp0722.vouchermanagement.member.model.Member;

import java.util.List;

public interface MemberRepository {
    List<Member> getBlackList();
    void insert(Member member);
}