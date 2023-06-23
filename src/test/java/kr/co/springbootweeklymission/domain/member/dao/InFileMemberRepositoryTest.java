package kr.co.springbootweeklymission.domain.member.dao;

import kr.co.springbootweeklymission.domain.member.entity.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class InFileMemberRepositoryTest {
    InFileMemberRepository memberRepository;

    @BeforeEach
    void beforeEach() {
        memberRepository = new InFileMemberRepository();
    }

    @Test
    @DisplayName("저장된_모든_블랙_회원을_CSV_파일로부터_조회한다 - SUCCESS")
    void 저장된_모든_블랙_회원을_CSV_파일로부터_조회한다() {
        //given & when
        List<Member> actual = memberRepository.findMembersByBlack();

        //then
        assertThat(actual).allMatch(Member::isBlackMember);
    }
}
