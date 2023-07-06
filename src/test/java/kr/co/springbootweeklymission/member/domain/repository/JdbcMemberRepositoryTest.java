package kr.co.springbootweeklymission.member.domain.repository;

import kr.co.springbootweeklymission.common.error.exception.NotFoundException;
import kr.co.springbootweeklymission.common.response.ResponseStatus;
import kr.co.springbootweeklymission.member.creators.MemberCreators;
import kr.co.springbootweeklymission.member.domain.entity.Member;
import kr.co.springbootweeklymission.member.domain.model.MemberStatus;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class JdbcMemberRepositoryTest {
    @Autowired
    JdbcMemberRepository memberRepository;

    Member member1;
    Member member2;
    Member member3;

    @BeforeAll
    void beforeAll() {
        member1 = MemberCreators.createBlackMember();
        member2 = MemberCreators.createBlackMember();
        member3 = MemberCreators.createWhiteMember();
    }

    @Test
    @Order(1)
    void save_블랙_회원을_등록_SUCCESS() {
        //given & when
        memberRepository.save(member1);
        Member actual = memberRepository.findById(member1.getMemberId())
                .orElseThrow(() -> new NotFoundException(ResponseStatus.FAIL_NOT_FOUND_MEMBER));

        //then
        assertThat(actual).isEqualTo(member1);
    }

    @Test
    @Order(2)
    void save_화이트_회원을_등록_SUCCESS() {
        //given & when
        memberRepository.save(member2);
        Member actual = memberRepository.findById(member2.getMemberId())
                .orElseThrow(() -> new NotFoundException(ResponseStatus.FAIL_NOT_FOUND_MEMBER));

        //then
        assertThat(actual).isEqualTo(member2);
    }

    @Test
    @Order(3)
    void findMembersByBlack_모든_블랙_회원을_조회_SUCCESS() {
        //given
        memberRepository.save(member3);

        //when
        List<Member> actual = memberRepository.findAllByBlack();

        //then
        assertThat(actual).hasSize(2);
    }

    @Test
    @Order(4)
    void findById_특정_회원을_조회_SUCCESS() {
        //given & when
        Member actual = memberRepository.findById(member1.getMemberId())
                .orElseThrow(() -> new NotFoundException(ResponseStatus.FAIL_NOT_FOUND_MEMBER));

        //then
        assertThat(actual).isEqualTo(member1);
    }

    @Test
    @Order(5)
    void findById_특정_회원을_조회_EMPTY() {
        //given & when & then
        assertThat(memberRepository.findById(UUID.randomUUID())).isEmpty();
    }

    @Test
    @Order(6)
    void update_특정_회원의_정보를_수정_SUCCESS() {
        //given
        Member updateMember = MemberCreators.updateMemberStatus(member1.getMemberId(), MemberStatus.WHITE);

        //when
        memberRepository.update(updateMember);
        Member actual = memberRepository.findById(member1.getMemberId())
                .orElseThrow(() -> new NotFoundException(ResponseStatus.FAIL_NOT_FOUND_MEMBER));

        //then
        assertThat(actual).isEqualTo(updateMember);
    }

    @Test
    @Order(7)
    void deleteById_특정_회원을_삭제_SUCCESS() {
        //given & when
        memberRepository.delete(member1);

        //then
        assertThat(memberRepository.findById(member1.getMemberId())).isEmpty();
    }
}
