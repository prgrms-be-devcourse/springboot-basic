package kr.co.springbootweeklymission.member.domain.repository;

import kr.co.springbootweeklymission.infrastructure.error.exception.NotFoundException;
import kr.co.springbootweeklymission.infrastructure.error.model.ResponseStatus;
import kr.co.springbootweeklymission.member.domain.creators.MemberCreators;
import kr.co.springbootweeklymission.member.domain.entity.Member;
import kr.co.springbootweeklymission.member.domain.model.MemberStatus;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class JdbcMemberRepositoryTest {

    @Autowired
    JdbcMemberRepository memberRepository;

    @Test
    @Order(1)
    void save_블랙_회원을_등록_SUCCESS() {
        //given
        Member member = MemberCreators.createBlackMember();

        //when
        memberRepository.save(member);
        Member actual = memberRepository.findById(member.getMemberId())
                .orElseThrow(() -> new NotFoundException(ResponseStatus.FAIL_NOT_FOUND_MEMBER));

        //then
        assertThat(actual).isEqualTo(member);
    }

    @Test
    @Order(1)
    void save_화이트_회원을_등록_SUCCESS() {
        //given
        Member member = MemberCreators.createWhiteMember();

        //when
        memberRepository.save(member);
        Member actual = memberRepository.findById(member.getMemberId())
                .orElseThrow(() -> new NotFoundException(ResponseStatus.FAIL_NOT_FOUND_MEMBER));

        //then
        assertThat(actual).isEqualTo(member);
    }

    @Test
    @Order(2)
    void findMembersByBlack_모든_블랙_회원을_조회_SUCCESS() {
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
    void findById_특정_회원을_조회_SUCCESS() {
        //given
        Member member = MemberCreators.createBlackMember();
        memberRepository.save(member);

        //when
        Member actual = memberRepository.findById(member.getMemberId())
                .orElseThrow(() -> new NotFoundException(ResponseStatus.FAIL_NOT_FOUND_MEMBER));

        //then
        assertThat(actual).isEqualTo(member);
    }

    @Test
    @Order(3)
    void findById_특정_회원을_조회_EMPTY() {
        //given & when & then
        assertThat(memberRepository.findById(UUID.randomUUID())).isEmpty();
    }

    @Test
    @Order(4)
    void update_특정_회원의_정보를_수정_SUCCESS() {
        //given
        Member member = MemberCreators.createBlackMember();
        memberRepository.save(member);
        Member updateMember = MemberCreators.updateMemberStatus(member.getMemberId(), MemberStatus.WHITE);

        //when
        memberRepository.update(updateMember);
        Member actual = memberRepository.findById(member.getMemberId())
                .orElseThrow(() -> new NotFoundException(ResponseStatus.FAIL_NOT_FOUND_MEMBER));

        //then
        assertThat(actual).isEqualTo(updateMember);
    }

    @Test
    @Order(5)
    void deleteById_특정_회원을_삭제_SUCCESS() {
        //given
        Member member = MemberCreators.createBlackMember();
        memberRepository.save(member);

        //when
        memberRepository.deleteById(member.getMemberId());

        //then
        assertThat(memberRepository.findById(member.getMemberId())).isEmpty();
    }
}
