package com.programmers.part1.member.repository;

import com.programmers.part1.configuration.AppConfiguration;
import com.programmers.part1.member.entity.MemberDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

class FileMemberRepositoryTest {

    ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(AppConfiguration.class);
    MemberRepository<UUID, MemberDto> memberRepository = ac.getBean(FileMemberRepository.class);

    @Test
    @DisplayName("모든 멤버들이 블랙 회원인지")
    void checkAllMemberIsBlack() throws IOException {
        List<MemberDto> blackMembers = memberRepository.findAllBlackMember();

        for(MemberDto memeber : blackMembers)
           Assertions.assertThat(memeber.getMemberType()).isEqualTo("Black");
    }

}