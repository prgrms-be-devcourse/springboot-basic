package org.programmers.VoucherManagement.member.infrastructure;

import org.programmers.VoucherManagement.member.domain.Member;
import org.programmers.VoucherManagement.member.domain.MemberStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MemberReaderRepository {
    /**
     * 전체 회원 조회
     *
     * @return List<Member> - 등록된 전체 회원
     */
    List<Member> findAll();

    /**
     * MemberStatus에 따른 회원 조회
     *
     * @return List<Member> - status에 해당하는 전체 회원
     */
    List<Member> findAllByMemberStatus(MemberStatus memberStatus);

    /**
     * 회원 ID를 이용해 회원 조회
     *
     * @param memberId
     * @return Optional<Member> - memberId값을 이용해 조회한 회원
     */
    Optional<Member> findById(UUID memberId);
}
