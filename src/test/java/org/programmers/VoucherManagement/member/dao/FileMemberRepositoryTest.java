package org.programmers.VoucherManagement.member.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.programmers.VoucherManagement.member.domain.Member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest(classes = {FileMemberRepository.class})
public class FileMemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("csv파일을읽고 Member리스트화하여 반환 테스트 - 성공")
    void csv파일을읽고_Member리스트화하여_반환_성공(){
        assertThatCode(() -> {
            List<Member> members = memberRepository.findAllByMemberStatus();
            assertTrue(!members.isEmpty());
        }).doesNotThrowAnyException();
    }
}

