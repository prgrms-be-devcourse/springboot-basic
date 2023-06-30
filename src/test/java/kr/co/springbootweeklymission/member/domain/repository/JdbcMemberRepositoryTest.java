package kr.co.springbootweeklymission.member.domain.repository;

import kr.co.springbootweeklymission.member.domain.creators.MemberCreators;
import kr.co.springbootweeklymission.member.domain.entity.Member;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

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
}
