package org.programmers.VoucherManagement.member.infrastructure;

import org.junit.jupiter.api.*;
import org.programmers.VoucherManagement.member.domain.Member;
import org.programmers.VoucherManagement.member.domain.MemberStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.annotation.Rollback;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class JdbcMemberRepositoryTest {
    private final JdbcMemberRepository memberRepository;

    JdbcMemberRepositoryTest(@Autowired DataSource dataSource) {
        memberRepository = new JdbcMemberRepository(dataSource);
    }

    private Member blackMember = new Member(UUID.randomUUID(), "kim", MemberStatus.BLACK);
    private Member whiteMember = new Member(UUID.randomUUID(), "park", MemberStatus.WHITE);

    @BeforeAll
    void initMember() {
        blackMember = new Member(UUID.randomUUID(), "kim", MemberStatus.BLACK);
        whiteMember = new Member(UUID.randomUUID(), "park", MemberStatus.WHITE);
        memberRepository.deleteAll();
    }

    @Order(1)
    @Test
    @Rollback(value = false)
    @DisplayName("블랙리스트 등급의 멤버를 새로 등록할 수 있다. - 성공")
    void insert_BlackMember_EqualsNewMember() {
        //given
        memberRepository.insert(blackMember);

        //when
        Member memberExpect = memberRepository.findById(blackMember.getMemberUUID()).get();

        //then
        assertThat(memberExpect).isEqualTo(blackMember);
    }

    @Order(2)
    @Test
    @Rollback(value = false)
    @DisplayName("일반 등급(white)의 멤버를 새로 등록할 수 있다. - 성공")
    void insert_WhiteMember_EqualsNewMember() {
        //given
        memberRepository.insert(whiteMember);

        //when
        Member memberExpect = memberRepository.findById(whiteMember.getMemberUUID()).get();

        //then
        assertThat(memberExpect).isEqualTo(whiteMember);
    }

    @Order(3)
    @Test
    @Rollback(value = false)
    @DisplayName("멤버 정보(등급)을 수정할 수 있다. - 성공")
    void update_Member_EqualsUpdateMember() {
        //given
        whiteMember.changeMemberStatus(MemberStatus.BLACK);
        memberRepository.update(whiteMember);

        //when
        Member memberExpect = memberRepository.findById(whiteMember.getMemberUUID()).get();

        //then
        assertThat(memberExpect).isEqualTo(whiteMember);
    }

    @Order(4)
    @Test
    @DisplayName("등록된 모든 멤버를 조회할 수 있다. - 성공")
    void findAll_Success() {
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList.size()).isEqualTo(2);
    }

    @Order(5)
    @Test
    @DisplayName("블랙리스트로 등록된 모든 멤버를 조회할 수 있다. - 성공")
    void findAllByMemberStatus_Success() {
        List<Member> blackMemberList = memberRepository.findAll();
        assertThat(blackMemberList.size()).isEqualTo(2);
    }

    @Order(6)
    @Test
    @DisplayName("memberId를 이용해 회원을 조회할 수 있다. - 성공")
    void findById_MemberId_EqualsFindMember() {
        //given
        UUID findMemberId = blackMember.getMemberUUID();

        //when
        Member memberExpect = memberRepository.findById(findMemberId).get();

        //then
        assertThat(memberExpect).isEqualTo(blackMember);
    }

    @Order(7)
    @Test
    @Rollback(value = false)
    @DisplayName("memberId를 이용해 회원을 삭제할 수 있다. - 성공")
    void delete_MemberId_Success() {
        //given
        UUID deleteMemberId = blackMember.getMemberUUID();
        Member deleteMember = memberRepository.findById(deleteMemberId).get();

        //when
        memberRepository.delete(deleteMember);

        //then
        int sizeExpect = memberRepository.findAll().size(); //1. 사이즈 비교
        assertThat(sizeExpect).isEqualTo(1);

        Optional<Member> optionalMember = memberRepository.findById(deleteMemberId); //2. 데이터베이스에 없는 값 확인
        assertThat(optionalMember).isEqualTo(Optional.empty());
    }
}

