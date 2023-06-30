package kr.co.springbootweeklymission.member.domain.creators;

import kr.co.springbootweeklymission.member.domain.entity.Member;
import kr.co.springbootweeklymission.member.domain.model.MemberStatus;

import java.util.UUID;

public class MemberCreators {
    private MemberCreators() {
    }

    public static Member createBlackMember() {
        return Member.builder()
                .memberId(UUID.randomUUID())
                .memberStatus(MemberStatus.BLACK)
                .build();
    }

    public static Member createWhiteMember() {
        return Member.builder()
                .memberId(UUID.randomUUID())
                .memberStatus(MemberStatus.WHITE)
                .build();
    }
}
