package kr.co.springbootweeklymission.member.domain.repository;

import kr.co.springbootweeklymission.infrastructure.error.exception.NotFoundException;
import kr.co.springbootweeklymission.infrastructure.error.model.ResponseStatus;
import kr.co.springbootweeklymission.member.domain.creators.MemberCreators;
import kr.co.springbootweeklymission.member.domain.entity.Member;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class JdbcMemberRepositoryTest {

    @Autowired
    JdbcMemberRepository memberRepository;

    @Test
    @Order(1)
    void 블랙_회원을_등록() {
        //given
        Member member = MemberCreators.createBlackMember();

        //when
        Member actual = memberRepository.save(member);

        //then
        assertThat(actual.getMemberId()).isEqualTo(member.getMemberId());
    }

    @Test
    @Order(2)
    void 모든_블랙_회원을_조회() {
        //given
        Member member1 = MemberCreators.createBlackMember();
        Member member2 = MemberCreators.createBlackMember();
        Member member3 = MemberCreators.createWhiteMember();
        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);

        //when
        List<Member> actual = memberRepository.findMembersByBlack();

        //then
        assertThat(actual).hasSize(3);
    }

    @Test
    @Order(3)
    void 특정_회원을_조회() {
        //given
        Member member = MemberCreators.createBlackMember();
        memberRepository.save(member);

        //when
        Member actual = memberRepository.findById(member.getMemberId())
                .orElseThrow(() -> new NotFoundException(ResponseStatus.FAIL_NOT_FOUND_MEMBER));

        //then
        assertThat(actual).isEqualTo(member);
    }
}
