package org.prgrms.kdt.member.presentation;

import org.prgrms.kdt.common.CommandStatus;
import org.prgrms.kdt.member.application.MemberService;
import org.prgrms.kdt.view.InputView;
import org.prgrms.kdt.view.OutputView;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class MemberController {

    private final MemberService memberService;
    private final List<String> data;

    public MemberController(MemberService memberService, List<String> data) {
        this.memberService = memberService;
        this.data = data;
    }

    public void play() {
        while (true) {
            memberService.init(data);
            InputView.initMemberMessage();
            String choiceMenu = InputView.input();
            CommandStatus status = choiceMenu(choiceMenu);
            executeMenu(status);
        }
    }

    private CommandStatus choiceMenu(String userInputMessage) {
        return CommandStatus.findByCommandType(userInputMessage);
    }

    private void executeMenu(CommandStatus status) {
        if (status == CommandStatus.EXIT) {
            exitCommandOrder();
            return;
        }

        if (status == CommandStatus.READ_ALL) {
            readAllBlackList();
            return;
        }

    }

    private void readAllBlackList() {
        OutputView.showAllBlackList(memberService.allVoucher());
    }

    private void exitCommandOrder() {
        InputView.closeScanner();
        OutputView.exit();
    }


}
