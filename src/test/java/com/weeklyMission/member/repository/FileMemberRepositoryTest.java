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
    String memberId;

    @BeforeAll
    void init() {
        fileMemberRepository = new FileMemberRepository("/src/test/resources/csv/blackListMember.csv");
        fileMemberRepository.init();

        memberId = UUID.randomUUID().toString();
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
    @DisplayName("전체 조회 테스트")
    void findAll() {
        //when
        fileMemberRepository.save(member);

        //then
        assertThat(fileMemberRepository.findAll().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("단일 조회 테스트")
    void findById() {
        //when
        Member saveMember = fileMemberRepository.save(member);

        //then
        assertThat(fileMemberRepository.findById(memberId).get()).isEqualTo(saveMember);
    }

    @Test
    @DisplayName("삭제 테스트")
    void deleteById() {
        //given
        Member saveMember = fileMemberRepository.save(member);
        assertThat(saveMember).isEqualTo(member);

        //when
        fileMemberRepository.deleteById(memberId);

        //then
        assertThat(fileMemberRepository.findById(memberId)).isNotPresent();
    }
}