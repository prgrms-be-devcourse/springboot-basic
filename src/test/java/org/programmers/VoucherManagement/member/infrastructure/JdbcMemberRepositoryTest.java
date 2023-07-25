package org.programmers.VoucherManagement.member.infrastructure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.VoucherManagement.member.domain.Member;
import org.programmers.VoucherManagement.member.domain.MemberStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import({JdbcMemberReaderRepository.class, JdbcMemberStoreRepository.class})
public class JdbcMemberRepositoryTest {
    @Autowired
    private JdbcMemberReaderRepository memberReaderRepository;

    @Autowired
    private JdbcMemberStoreRepository memberStoreRepository;

    private Member blackMember = new Member(UUID.randomUUID(), "kim", MemberStatus.BLACK);
    private Member whiteMember = new Member(UUID.randomUUID(), "park", MemberStatus.WHITE);

    @BeforeEach
    void initMember() {
        blackMember = new Member(UUID.randomUUID(), "kim", MemberStatus.BLACK);
        whiteMember = new Member(UUID.randomUUID(), "park", MemberStatus.WHITE);
    }

    @Test
    @DisplayName("블랙리스트 등급의 멤버를 새로 등록할 수 있다. - 성공")
    void insert_BlackMember_EqualsNewMember() {
        //when
        memberStoreRepository.insert(blackMember);

        //then
        Member memberExpect = memberReaderRepository.findById(blackMember.getMemberUUID()).get();
        assertThat(memberExpect).usingRecursiveComparison().isEqualTo(blackMember);
    }

    @Test
    @DisplayName("일반 등급(white)의 멤버를 새로 등록할 수 있다. - 성공")
    void insert_WhiteMember_EqualsNewMember() {
        //when
        memberStoreRepository.insert(whiteMember);

        //then
        Member memberExpect = memberReaderRepository.findById(whiteMember.getMemberUUID()).get();
        assertThat(memberExpect).usingRecursiveComparison().isEqualTo(whiteMember);
    }

    @Test
    @DisplayName("멤버 정보(등급)을 수정할 수 있다. - 성공")
    void update_Member_EqualsUpdateMember() {
        //given
        memberStoreRepository.insert(whiteMember);
        whiteMember.changeMemberStatus(MemberStatus.BLACK);

        //when
        memberStoreRepository.update(whiteMember);

        //then
        Member memberExpect = memberReaderRepository.findById(whiteMember.getMemberUUID()).get();
        assertThat(memberExpect).usingRecursiveComparison().isEqualTo(whiteMember);
    }

    @Test
    @DisplayName("등록된 모든 멤버를 조회할 수 있다. - 성공")
    void findAll_Success() {
        //given
        memberStoreRepository.insert(whiteMember);
        memberStoreRepository.insert(blackMember);

        //when
        List<Member> memberList = memberReaderRepository.findAll();

        //then
        assertThat(memberList.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("블랙리스트로 등록된 모든 멤버를 조회할 수 있다. - 성공")
    void findAllByMemberStatus_Success() {
        //given
        memberStoreRepository.insert(whiteMember);
        memberStoreRepository.insert(blackMember);

        //when
        List<Member> blackMemberList = memberReaderRepository.findAllByMemberStatus(MemberStatus.BLACK);

        //then
        assertThat(blackMemberList.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("memberId를 이용해 회원을 조회할 수 있다. - 성공")
    void findById_MemberId_EqualsFindMember() {
        //given
        memberStoreRepository.insert(blackMember);
        UUID findMemberId = blackMember.getMemberUUID();

        //when
        Member memberExpect = memberReaderRepository.findById(findMemberId).get();

        //then
        assertThat(memberExpect).usingRecursiveComparison().isEqualTo(blackMember);
    }

    @Test
    @DisplayName("memberId를 이용해 회원을 삭제할 수 있다. - 성공")
    void delete_MemberId_Success() {
        //given
        memberStoreRepository.insert(blackMember);
        UUID deleteMemberId = blackMember.getMemberUUID();

        //when
        memberStoreRepository.delete(deleteMemberId);

        //then
        int sizeExpect = memberReaderRepository.findAll().size(); //1. 사이즈 비교
        assertThat(sizeExpect).isEqualTo(0);

        Optional<Member> optionalMember = memberReaderRepository.findById(deleteMemberId); //2. 데이터베이스에 없는 값 확인
        assertThat(optionalMember).isEqualTo(Optional.empty());
    }
}
