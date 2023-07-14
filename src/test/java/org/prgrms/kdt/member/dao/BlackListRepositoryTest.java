package org.prgrms.kdt.member.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.prgrms.kdt.member.domain.Member;
import org.prgrms.kdt.member.domain.MemberStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


class BlackListRepositoryTest {
    static MemberLoader mockMemberLoader;

    @Configuration
    static class TestConfig{

        @Bean
        BlackListRepository blackListRepository(){
            mockMemberLoader = Mockito.mock(MemberLoader.class);
            return new BlackListRepository(mockMemberLoader);
        }
    }

    BlackListRepository blackListRepository;
    AnnotationConfigApplicationContext testContext;

    @BeforeEach
    void setup() {
        testContext = new AnnotationConfigApplicationContext(TestConfig.class);
        blackListRepository = testContext.getBean(BlackListRepository.class);
        BDDMockito.given(mockMemberLoader.loadFileToMemoryMember()).willReturn(new ConcurrentHashMap<>());
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

    @Test
    @DisplayName("tetContext를 close하며 blackListRepository의 빈이 소멸할 때 fileWrite를 호출하는지 확인")
    void fileWrite(){
        //when
        testContext.close();

        //then
        verify(mockMemberLoader, times(1)).saveMemoryMemberToFile(anyMap());
    }
}