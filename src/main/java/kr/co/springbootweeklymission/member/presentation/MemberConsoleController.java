package kr.co.springbootweeklymission.member.presentation;

import kr.co.springbootweeklymission.console.InputView;
import kr.co.springbootweeklymission.console.OutputView;
import kr.co.springbootweeklymission.member.application.MemberService;
import kr.co.springbootweeklymission.member.presentation.dto.request.MemberReqDTO;
import kr.co.springbootweeklymission.member.presentation.dto.response.MemberResDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class MemberConsoleController {
    private final MemberService memberService;

    public void createMember() {
        OutputView.outputCreateMember();
        OutputView.outputMemberStatus();
        final MemberReqDTO.CREATE createMember = MemberReqDTO.CREATE.builder()
                .memberStatus(InputView.inputMemberStatus())
                .build();
        memberService.createMember(createMember);
    }

    public MemberResDTO.READ getMemberById() {
        UUID memberId = UUID.fromString(InputView.inputMemberId());
        return memberService.getMemberById(memberId);
    }

    public List<MemberResDTO.READ> getBlackMembers() {
        return memberService.getBlackMembers();
    }

    public void updateMemberById() {
        OutputView.outputUpdateMember();
        OutputView.outputMemberStatus();
        UUID memberId = UUID.fromString(InputView.inputMemberId());
        final MemberReqDTO.UPDATE updateMember = MemberReqDTO.UPDATE.builder()
                .memberStatus(InputView.inputMemberStatus())
                .build();
        memberService.updateMemberById(memberId, updateMember);
    }

    public void deleteMemberById() {
        OutputView.outputDeleteMember();
        UUID memberId = UUID.fromString(InputView.inputMemberId());
        memberService.deleteMemberById(memberId);
    }
}
