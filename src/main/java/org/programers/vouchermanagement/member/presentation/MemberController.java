package org.programers.vouchermanagement.member.presentation;

import org.programers.vouchermanagement.member.application.MemberService;
import org.programers.vouchermanagement.member.domain.MemberStatus;
import org.programers.vouchermanagement.member.dto.request.MemberCreationRequest;
import org.programers.vouchermanagement.member.dto.request.MemberUpdateRequest;
import org.programers.vouchermanagement.member.dto.response.MembersResponse;
import org.programers.vouchermanagement.view.Command;
import org.programers.vouchermanagement.view.InputView;
import org.programers.vouchermanagement.view.OutputView;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    public void run() {
        OutputView.outputCommand();
        Command command = InputView.inputCommand();

        if (command.isCreate()) {
            OutputView.outputMemberStatus();
            MemberStatus status = InputView.inputMemberStatus();
            memberService.save(new MemberCreationRequest(status));
            return;
        }

        if(command.isRead()) {
            MembersResponse response = memberService.findAll();
            OutputView.outputMembers(response);
            return;
        }

        if (command.isUpdate()) {
            OutputView.outputUUIDComment();
            UUID id = InputView.inputUUID();
            OutputView.outputMemberStatus();
            MemberStatus status = InputView.inputMemberStatus();
            memberService.update(new MemberUpdateRequest(id, status));
            return;
        }

        if (command.isDelete()) {
            OutputView.outputUUIDComment();
            UUID id = InputView.inputUUID();
            memberService.deleteById(id);
        }
    }
}
