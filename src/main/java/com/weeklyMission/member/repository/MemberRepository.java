package com.weeklyMission.member.repository;

import com.weeklyMission.member.domain.Member;
import java.util.List;

public interface MemberRepository {
    Member save(Member member);

    List<Member> findAll();
}
