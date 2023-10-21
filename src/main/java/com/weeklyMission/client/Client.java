package com.weeklyMission.client;

import com.weeklyMission.console.ConsoleIO;
import com.weeklyMission.member.controller.MemberController;
import com.weeklyMission.member.domain.Member;
import com.weeklyMission.voucher.controller.VoucherController;
import com.weeklyMission.voucher.domain.Voucher;
import com.weeklyMission.voucher.dto.VoucherResponse;
import com.weeklyMission.exception.IncorrectInputException;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class Client {

    private static final String VOUCHER = "voucher";
    private static final String MEMBER = "member";
    private static final String EXIT = "exit";
    private static final String CREATE = "create";
    private static final String LIST = "list";

    private final ConsoleIO consoleIOHandler;
    private final MemberController memberController;
    private final VoucherController voucherController;

    public Client(ConsoleIO consoleIOHandler, MemberController memberController, VoucherController voucherController) {
        this.consoleIOHandler = consoleIOHandler;
        this.memberController = memberController;
        this.voucherController = voucherController;
    }

    public void run() {
        String mode = consoleIOHandler.printSelectMode();
        switch (mode) {
            case VOUCHER -> {
                voucherMode();
            }
            case MEMBER -> {
                memberMode();
            }
            case EXIT -> {
                consoleIOHandler.printExitMessage();
                System.exit(0);
            }
            default -> {
                throw new IncorrectInputException("mode", mode, "목록에 있는 것들 중 선택하세요.");
            }
        }
    }

    private void voucherMode() {
        String function = consoleIOHandler.printSelectVoucherFunction();
        switch (function) {
            case CREATE -> {
                VoucherType voucherType = VoucherType.of(consoleIOHandler.printSelectVoucherType());
                Voucher voucher = voucherType.giveVoucher(consoleIOHandler);
                VoucherResponse voucherDto = voucherController.create(voucher);
                consoleIOHandler.printSuccessCreate(voucherDto);
            }
            case LIST -> {
                List<VoucherResponse> voucherListDto = voucherController.getVoucherList();
                consoleIOHandler.printSuccessGetAllList(voucherListDto);
            }
            default -> {
                throw new IncorrectInputException("function", function, "목록에 있는 것들 중 선택하세요.");
            }
        }
    }

    private void memberMode() {
        String function = consoleIOHandler.printSelectMemberFunction();
        switch (function){
            case LIST ->{
                List<Member> blackList = memberController.getBlackList();
                consoleIOHandler.printSuccessGetBlackList(blackList);
            }
            default ->{
                throw new IncorrectInputException("function", function, "목록에 있는 것들 중 선택하세요.");
            }
        }
    }

}
