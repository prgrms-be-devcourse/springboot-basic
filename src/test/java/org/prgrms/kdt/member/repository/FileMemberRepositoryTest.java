package org.prgrms.kdt.member.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.member.domain.Member;
import org.prgrms.kdt.member.domain.MemberStatus;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class FileMemberRepositoryTest {
    public FileMemberRepository fileMemberRepository;

    @BeforeEach
    void setup() {
        fileMemberRepository = new FileMemberRepository();
        fileMemberRepository.insert(new Member(UUID.randomUUID(), "yaho", MemberStatus.BLACK));
        fileMemberRepository.insert(new Member(UUID.randomUUID(), "abc", MemberStatus.BLACK));
        fileMemberRepository.insert(new Member(UUID.randomUUID(), "defg", MemberStatus.BLACK));
    }

    @Test
    void findAllBlackMember() {
        //when
        List<Member> foundBlackMembers = fileMemberRepository.findAllBlackMember();

        //then
        assertThat(foundBlackMembers.size(), is(3));
    }
}