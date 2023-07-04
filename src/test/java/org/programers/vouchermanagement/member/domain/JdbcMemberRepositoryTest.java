package org.programers.vouchermanagement.member.domain;

import org.junit.jupiter.api.*;
import org.programers.vouchermanagement.global.exception.NoSuchEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class JdbcMemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    private Member member;

    @Order(1)
    @Test
    void 회원을_저장한다() {
        // given
        member = new Member(MemberStatus.NORMAL);

        // when
        Member result = memberRepository.save(member);

        // then
        assertThat(result.getId()).isEqualTo(member.getId());
    }

    @Order(2)
    @Test
    void 회원을_아이디로_조회한다() {
        // given & when
        Member result = memberRepository.getById(member.getId());

        // then
        assertThat(result.getId()).isEqualTo(member.getId());
    }

    @Order(3)
    @Test
    void 회원을_모두_조회한다() {
        // given
        memberRepository.save(new Member(MemberStatus.NORMAL));

        // when
        List<Member> result = memberRepository.findAll();

        // then
        assertThat(result).hasSize(2);
    }

    @Order(4)
    @Test
    void 회원을_수정한다() {
        // given
        Member updateMember = new Member(member.getId(), MemberStatus.BLACK);

        // when
        memberRepository.update(updateMember);

        // then
        assertThat(memberRepository.getById(member.getId()).getStatus()).isEqualTo(MemberStatus.BLACK);
    }

    @Order(5)
    @Test
    void 회원을_삭제한다() {
        // given && when
        memberRepository.deleteById(member.getId());

        // then
        assertThatThrownBy(() -> memberRepository.getById(member.getId()))
                .isInstanceOf(NoSuchEntityException.class)
                .hasMessage("존재하지 않는 회원입니다.");
    }
}
