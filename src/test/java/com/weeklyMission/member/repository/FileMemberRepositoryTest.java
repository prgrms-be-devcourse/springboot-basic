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

    @BeforeAll
    void init() {
        fileMemberRepository = new FileMemberRepository(
            "/src/test/resources/csv/blackListMember.csv");
        fileMemberRepository.init();
    }

    @Test
    @Order(1)
    @DisplayName("저장 테스트")
    void save() {
        //given
        Member member = new Member(UUID.randomUUID(), "김똥깡", 20, "욕설");

        //when
        Member saveMember = fileMemberRepository.save(member);

        //then
        assertThat(member).isEqualTo(saveMember);
    }

    @Test
    @Order(2)
    @DisplayName("파일 동기화 테스트")
    void write() {
        //given
        int basicSize = fileMemberRepository.findAll().size();

        //when
        fileMemberRepository.close();
        fileMemberRepository.init();

        //then
        assertThat(basicSize).isEqualTo(fileMemberRepository.findAll().size());
    }

}