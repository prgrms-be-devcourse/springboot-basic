package org.programers.vouchermanagement.member.domain;

import org.programers.vouchermanagement.global.exception.NoSuchDomainException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MemberRepository {

    Member save(Member member);

    Optional<Member> findById(UUID id);

    default Member getById(UUID id) {
        return findById(id)
                .orElseThrow(() -> new NoSuchDomainException("존재하지 않는 회원입니다."));
    }

    List<Member> findAll();

    void update(Member member);

    void deleteById(UUID id);
}
