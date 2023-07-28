package org.programmers.VoucherManagement.member.application;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MemberServiceMockTest {
//
//    @InjectMocks
//    MemberService memberService;
//
//    @Mock
//    MemberStoreRepository memberStoreRepository;
//
//    @Mock
//    MemberReaderRepository memberReaderRepository;
//
//    @Test
//    @DisplayName("회원 정보(status)를 BLACK으로 수정한다. - 성공")
//    void updateMember_IdAndDto_Success() {
//        //given
//        UUID memberId = UUID.randomUUID();
//        Member saveMember = new Member(memberId, "Kim", MemberStatus.WHITE);
//        memberStoreRepository.insert(saveMember);
//        MemberUpdateRequest updateRequestDto = new MemberUpdateRequest(MemberStatus.BLACK);
//
//        //mocking
//        given(memberReaderRepository.findById(memberId)).willReturn(Optional.of(saveMember));
//
//        //when
//        memberService.updateMember(memberId, updateRequestDto);
//
//        //then
//        Member updateMember = memberReaderRepository.findById(memberId).get();
//        assertThat(updateMember.getMemberStatus()).isEqualTo(updateRequestDto.memberStatus());
//    }
//
//    @Test
//    @DisplayName("회원Id를 입력받아 회원을 삭제할 수 있다. - 성공")
//    void deleteMember_Id_Success() {
//        //given
//        UUID memberId = UUID.randomUUID();
//        Member saveMember = new Member(memberId, "Kim", MemberStatus.BLACK);
//        memberStoreRepository.insert(saveMember);
//
//        //when
//        memberService.deleteMember(saveMember.getMemberId());
//
//        //then
//        verify(memberStoreRepository, times(1)).delete(memberId);
//    }
//
//    @Test
//    @DisplayName("저장되어 있는 모든 멤버를 조회할 수 있다. - 성공")
//    void getAllMembers_EqualsListOfMembers() {
//        //given
//        Member member1 = new Member(UUID.randomUUID(), "Kim", MemberStatus.BLACK);
//        Member member2 = new Member(UUID.randomUUID(), "Park", MemberStatus.WHITE);
//        List<Member> memberList = Arrays.asList(member1, member2);
//
//        //mocking
//        given(memberReaderRepository.findAll()).willReturn(memberList);
//
//        //when
//        MemberGetResponses response = memberService.getAllMembers();
//
//        //then
//        List<MemberGetResponse> responseExpect = memberList.stream()
//                .map(MemberGetResponse::toDto)
//                .collect(Collectors.toList());
//
//        assertThat(response).isNotNull();
//        assertThat(response.getGetMemberListRes()).usingRecursiveComparison().isEqualTo(responseExpect);
//    }
//
//    @Test
//    @DisplayName("저장되어 있는 모든 블랙리스트 멤버를 조회할 수 있다. - 성공")
//    void getAllBlackMembers_EqualsListOfMembers() {
//        //given
//        Member member1 = new Member(UUID.randomUUID(), "Kim", MemberStatus.BLACK);
//        Member member2 = new Member(UUID.randomUUID(), "Park", MemberStatus.BLACK);
//        Member member3 = new Member(UUID.randomUUID(), "Lee", MemberStatus.WHITE);
//        List<Member> blackMemberList = Stream.of(member1, member2, member3)
//                .filter(m -> m.getMemberStatus() == MemberStatus.BLACK)
//                .collect(Collectors.toList());
//
//        //mocking
//        given(memberReaderRepository.findAllByMemberStatus(MemberStatus.BLACK)).willReturn(blackMemberList);
//
//        //when
//        MemberGetResponses response = memberService.getAllBlackMembers();
//
//        //then
//        List<MemberGetResponse> responseExpect = blackMemberList.stream()
//                .map(MemberGetResponse::toDto)
//                .collect(Collectors.toList());
//
//        assertThat(response).isNotNull();
//        assertThat(response.getGetMemberListRes()).usingRecursiveComparison().isEqualTo(responseExpect);
//    }
}
