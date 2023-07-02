package org.prgrms.kdt.member.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.prgrms.kdt.member.domain.Member;
import org.prgrms.kdt.member.domain.MemberStatus;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class FileMemberRepositoryTest {
    private FileMemberRepository fileMemberRepository;
    private MemberLoader mockMemberLoader;

    @BeforeEach
    void setup() {
        mockMemberLoader = Mockito.mock(MemberLoader.class);
        fileMemberRepository = new FileMemberRepository(mockMemberLoader);
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