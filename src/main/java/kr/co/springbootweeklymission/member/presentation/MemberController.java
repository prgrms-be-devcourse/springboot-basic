package kr.co.springbootweeklymission.member.presentation;

import kr.co.springbootweeklymission.member.application.MemberService;
import kr.co.springbootweeklymission.member.presentation.dto.request.MemberReqDTO;
import kr.co.springbootweeklymission.member.presentation.dto.response.MemberResDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    public void createMember(MemberReqDTO.CREATE create) {
        memberService.createMember(create);
    }

    public MemberResDTO.READ getMemberById(UUID memberId) {
        return memberService.getMemberById(memberId);
    }

    public List<MemberResDTO.READ> getMembersByBlack() {
        return memberService.getMembersByBlack();
    }

    public void updateMemberById(UUID memberId,
                                 MemberReqDTO.UPDATE update) {
        memberService.updateMemberById(memberId, update);
    }

    public void deleteMemberById(UUID memberId) {
        memberService.deleteMemberById(memberId);
    }
}
