package org.programmers.VoucherManagement.member.infrastructure;

import org.programmers.VoucherManagement.member.domain.Member;

import java.util.UUID;

public interface MemberStoreRepository {
    /**
     * db에 회원 저장
     *
     * @param member
     * @return Member - 저장 완료한 회원
     */
    Member insert(Member member); //저장

    /**
     * db에 저장된 회원 수정
     *
     * @param member
     */
    void update(Member member); //수정

    /**
     * db에 저장된 회원 삭제
     *
     * @param memberId
     */
    void delete(UUID memberId); //삭제
}
