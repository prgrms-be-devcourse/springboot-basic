package com.weeklyMission.member.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.weeklyMission.member.domain.Member;
import java.util.UUID;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;


@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FileMemberRepositoryTest {

    FileMemberRepository fileMemberRepository;

    Member member;
    UUID memberId;

    @BeforeAll
    void init() {
        fileMemberRepository = new FileMemberRepository("/src/test/resources/csv/blackListMember.csv");
        fileMemberRepository.init();

        memberId = UUID.randomUUID();
        member = new Member(memberId, "김철수", "kig2454@gmail.com", 20);
    }

    @Test
    @DisplayName("저장 테스트")
    void save() {
        //when
        Member saveMember = fileMemberRepository.save(member);

        //then
        assertThat(member).isEqualTo(saveMember);
    }

    @Test
    void findAll() {
        //given
        Member member = new Member(UUID.randomUUID(), "김강훈", "abc1234@gmail.com", 21);

        //when
        fileMemberRepository.save(member);

        //then
        assertThat(fileMemberRepository.findAll().size()).isEqualTo(1);
    }

    @Test
    void findById() {
        //given
        UUID id = UUID.randomUUID();
        Member member = new Member(id, "김영수", "ghj5678@naver.com", 30);

        //when
        Member saveMember = fileMemberRepository.save(member);

        //then
        assertThat(fileMemberRepository.findById(id).get()).isEqualTo(member);
    }

    @Test
    void deleteById() {
        //when
        fileMemberRepository.deleteById(memberId);

        assertThat(fileMemberRepository.findById(memberId)).isNotPresent();
    }
}