package org.programmers.VoucherManagement.member.application;

import com.github.f4b6a3.ulid.UlidCreator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.programmers.VoucherManagement.member.application.dto.MemberGetResponse;
import org.programmers.VoucherManagement.member.application.dto.MemberGetResponses;
import org.programmers.VoucherManagement.member.application.dto.MemberUpdateRequest;
import org.programmers.VoucherManagement.member.domain.Member;
import org.programmers.VoucherManagement.member.domain.MemberStatus;
import org.programmers.VoucherManagement.member.exception.MemberException;
import org.programmers.VoucherManagement.member.infrastructure.MemberReaderRepository;
import org.programmers.VoucherManagement.member.infrastructure.MemberStoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.programmers.VoucherManagement.global.response.ErrorCode.FAIL_TO_DELETE_MEMBER;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
public class MemberServiceTest {
    @Autowired
    MemberService memberService;

    @Autowired
    MemberReaderRepository memberReaderRepository;

    @Autowired
    MemberStoreRepository memberStoreRepository;

    @Test
    @DisplayName("회원 정보(status)를 BLACK으로 수정한다. - 성공")
    void updateMember_IdAndDto_Success() {
        //given
        String memberId = UlidCreator.getUlid().toString();
        Member saveMember = new Member(memberId, "Kim", MemberStatus.WHITE);
        memberStoreRepository.insert(saveMember);
        MemberUpdateRequest updateRequestDto = new MemberUpdateRequest(MemberStatus.BLACK);

        //when
        memberService.updateMember(memberId, updateRequestDto);

        //then
        Member updateMember = memberReaderRepository.findById(memberId).get();
        assertThat(updateMember.getMemberStatus()).isEqualTo(updateRequestDto.memberStatus());
    }

    @Test
    @DisplayName("회원Id를 입력받아 회원을 삭제할 수 있다. - 성공")
    void deleteMember_Id_Success() {
        //given
        String memberId = UlidCreator.getUlid().toString();
        Member saveMember = new Member(memberId, "Kim", MemberStatus.BLACK);
        memberStoreRepository.insert(saveMember);

        //when
        memberService.deleteMember(saveMember.getMemberId());

        //then
        Optional<Member> optionalMember = memberReaderRepository.findById(memberId);
        assertThat(optionalMember).isEqualTo(Optional.empty());
    }

    @Test
    @DisplayName("회원Id를 입력받아 회원을 삭제할 수 있다. - 실패")
    void deleteMember_Id_ThrowMemberException() {
        //given
        String memberId = UlidCreator.getUlid().toString();
        Member saveMember = new Member(memberId, "Kim", MemberStatus.BLACK);
        memberStoreRepository.insert(saveMember);

        //when
        memberService.deleteMember(saveMember.getMemberId());

        //then
        String strangeId = UlidCreator.getUlid().toString();
        assertThatThrownBy(() -> memberService.deleteMember(strangeId))
                .isInstanceOf(MemberException.class)
                .hasMessage(FAIL_TO_DELETE_MEMBER.getMessage());
    }

    @ParameterizedTest
    @DisplayName("저장되어 있는 모든 멤버를 조회할 수 있다. - 성공")
    @MethodSource("member_Data")
    void getAllMembers_EqualsListOfMembers(List<Member> members) {
        //given
        members.forEach(member -> memberStoreRepository.insert(member));

        //when
        MemberGetResponses response = memberService.getAllMembers();

        //then
        List<MemberGetResponse> responseExpect = members.stream()
                .map(MemberGetResponse::toDto)
                .collect(Collectors.toList());

        assertThat(response).isNotNull();
        assertThat(response.getGetMemberListRes()).usingRecursiveComparison().isEqualTo(responseExpect);
    }

    @ParameterizedTest
    @DisplayName("저장되어 있는 모든 블랙리스트 멤버를 조회할 수 있다. - 성공")
    @MethodSource("member_Data")
    void getAllBlackMembers_EqualsListOfMembers(List<Member> members) {
        //given
        members.forEach(member -> memberStoreRepository.insert(member));
        List<Member> blackMemberList = members.stream()
                .filter(m -> m.getMemberStatus() == MemberStatus.BLACK)
                .toList();

        //when
        MemberGetResponses response = memberService.getAllBlackMembers();

        //then
        List<MemberGetResponse> responseExpect = blackMemberList.stream()
                .map(MemberGetResponse::toDto)
                .collect(Collectors.toList());

        assertThat(response).isNotNull();
        assertThat(response.getGetMemberListRes()).usingRecursiveComparison().isEqualTo(responseExpect);
    }

    private static Stream<List<Member>> member_Data() {
        Member member1 = new Member(UlidCreator.getUlid().toString(), "Kim", MemberStatus.BLACK);
        Member member2 = new Member(UlidCreator.getUlid().toString(), "Park", MemberStatus.WHITE);
        return Stream.of(
                List.of(member1, member2)
        );
    }
}
