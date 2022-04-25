package com.pppp0722.vouchermanagement.member.repository;

import com.pppp0722.vouchermanagement.member.model.Member;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MemberRepository {

    Optional<Member> insert(Member member);

    List<Member> findAll();

    Optional<Member> findById(UUID memberId);

    Optional<Member> update(Member member);

    Optional<Member> delete(Member member);
}