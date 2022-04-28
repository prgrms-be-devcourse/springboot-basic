package com.pppp0722.vouchermanagement.member.repository;

import com.pppp0722.vouchermanagement.member.model.Member;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MemberRepository {

    Member insert(Member member);

    List<Member> findAll();

    Optional<Member> findById(UUID memberId);

    Member update(Member member);

    Member delete(Member member);

    void deleteAll();
}