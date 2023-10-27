package com.weeklyMission.member.repository;

import com.weeklyMission.member.domain.Member;
import com.weeklyMission.voucher.domain.Voucher;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MemberRepository {
    Member save(Member member);

    List<Member> findAll();

    Optional<Member> findById(UUID id);

    void deleteById(UUID id);
}
