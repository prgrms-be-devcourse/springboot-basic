package org.prgrms.kdt.member.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.prgrms.kdt.member.domain.Member;
import org.prgrms.kdt.member.domain.MemberStatus;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class BlackListRepositoryTest {
    BlackListRepository blackListRepository;
    MemberLoader mockMemberLoader;

    @BeforeEach
    void setup() {
        mockMemberLoader = Mockito.mock(MemberLoader.class);
        blackListRepository = new BlackListRepository(mockMemberLoader);
        blackListRepository.insert(new Member(UUID.randomUUID(), "yaho", MemberStatus.BLACK));
        blackListRepository.insert(new Member(UUID.randomUUID(), "abc", MemberStatus.BLACK));
        blackListRepository.insert(new Member(UUID.randomUUID(), "defg", MemberStatus.BLACK));
    }

    @Test
    @DisplayName("블랙리스트 맴버 전체 조회")
    void findAllBlackMember() {
        //when
        List<Member> foundBlackMembers = blackListRepository.findAllBlackMember();

        //then
        assertThat(foundBlackMembers.size(), is(3));
    }
}