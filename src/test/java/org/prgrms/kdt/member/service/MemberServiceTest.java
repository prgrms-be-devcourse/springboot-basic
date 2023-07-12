package org.prgrms.kdt.member.service;

import org.junit.jupiter.api.*;
import org.prgrms.kdt.member.dao.JdbcMemberRepository;
import org.prgrms.kdt.member.domain.Member;
import org.prgrms.kdt.member.domain.MemberStatus;
import org.prgrms.kdt.member.dto.CreateMemberRequest;
import org.prgrms.kdt.member.dto.MemberResponse;
import org.prgrms.kdt.member.dto.MembersResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    JdbcMemberRepository jdbcMemberRepository;

    @BeforeEach
    void setup(){
        jdbcMemberRepository.insert(new Member("lala", MemberStatus.BLACK));
        jdbcMemberRepository.insert(new Member("pipo", MemberStatus.BLACK));
        jdbcMemberRepository.insert(new Member("pipo", MemberStatus.COMMON));
    }

    @Test
    @DisplayName("멤버를 저장하고 제대로 멤버를 반환하는지 확인")
    void createMember_correctRequest_correctMemberName() {
        //given
        CreateMemberRequest request = new CreateMemberRequest("james", MemberStatus.COMMON);

        //when
        MemberResponse result = memberService.createMember(request);

        //then
        String resultName = result.memberName();
        assertThat(resultName).isEqualTo("james");
    }

    @Test
    @DisplayName("db에 저장되어있는 2명의 블랙멤버들 잘 찾아오는지 확인")
    void findAllBlackMember_correctSize(){
        //when
        MembersResponse findBlackMembers = memberService.findAllBlackMember();

        //then
        int findBlackMembersSize = findBlackMembers.getMembers().size();
        assertThat(findBlackMembersSize).isEqualTo(2);
    }

    @Test
    @DisplayName("db에 있는 멤버 3명 잘 찾아오는지 확인")
    void findAllMember_correctAllMember(){
        //when
        MembersResponse findAllMember = memberService.findAllMember();

        //then
        int findAllMembersize = findAllMember.getMembers().size();
        assertThat(findAllMembersize).isEqualTo(3);
    }
}