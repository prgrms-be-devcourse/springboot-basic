package kr.co.springbootweeklymission.domain.member.dao;

import kr.co.springbootweeklymission.domain.member.entity.Member;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository {
    List<Member> findMembersByBlack();
}
