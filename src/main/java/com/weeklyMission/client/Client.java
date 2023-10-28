package com.weeklyMission.client;

import com.weeklyMission.console.ConsoleIO;
import com.weeklyMission.member.controller.MemberController;
import com.weeklyMission.member.dto.MemberRequest;
import com.weeklyMission.member.dto.MemberResponse;
import com.weeklyMission.voucher.controller.VoucherController;
import com.weeklyMission.voucher.dto.VoucherRequest;
import com.weeklyMission.voucher.dto.VoucherResponse;
import com.weeklyMission.exception.IncorrectInputException;
import com.weeklyMission.wallet.controller.WalletController;
import java.util.List;
import java.util.UUID;

public class Client {

    private static final String VOUCHER = "voucher";
    private static final String MEMBER = "member";
    private static final String WALLET = "wallet";
    private static final String CREATE = "create";
    private static final String LIST = "list";
    private static final String FIND = "find";
    private static final String DELETE = "delete";
    private static final String FINDMEMBER = "findmember";
    private static final String FINDVOUCHER = "findvoucher";
    private static final String EXIT = "exit";

    private final ConsoleIO consoleIOHandler;
    private final MemberController memberController;
    private final VoucherController voucherController;
    private final WalletController walletController;

    public Client(ConsoleIO consoleIOHandler, MemberController memberController, VoucherController voucherController,
        WalletController walletController) {
        this.consoleIOHandler = consoleIOHandler;
        this.memberController = memberController;
        this.voucherController = voucherController;
        this.walletController = walletController;
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
            case WALLET -> {
                walletMode();
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
                VoucherRequest voucherRequest = new VoucherRequest(voucherType.getType(),
                    UUID.randomUUID(), consoleIOHandler.printAmountCommand());
                voucherController.create(voucherRequest);
                consoleIOHandler.printSuccessCreate();
            }
            case LIST -> {
                List<VoucherResponse> voucherListDto = voucherController.findAll();
                consoleIOHandler.printSuccessGetVoucherList(voucherListDto);
            }
            case FIND -> {
                UUID id = consoleIOHandler.commandVoucherId();
                VoucherResponse voucherResponse = voucherController.findById(id);
                consoleIOHandler.printSuccessGet(voucherResponse);
            }
            case DELETE -> {
                UUID id = consoleIOHandler.commandVoucherId();
                voucherController.deleteById(id);
                consoleIOHandler.printSuccessDelete();
            }
            default -> {
                throw new IncorrectInputException("function", function, "목록에 있는 것들 중 선택하세요.");
            }
        }
    }

    private void memberMode() {
        String function = consoleIOHandler.printSelectMemberFunction();
        switch (function){
            case CREATE -> {
                MemberRequest memberRequest = new MemberRequest(UUID.randomUUID(), consoleIOHandler.nameCommand(), consoleIOHandler.emailCommand(), consoleIOHandler.ageCommand());
                memberController.create(memberRequest);
                consoleIOHandler.printSuccessCreate();
            }
            case LIST ->{
                List<MemberResponse> memberList = memberController.findAll();
                consoleIOHandler.printSuccessGetMemberList(memberList);
            }
            case FIND -> {
                UUID id = consoleIOHandler.commandMemberId();
                MemberResponse memberResponse = memberController.findById(id);
                consoleIOHandler.printSuccessGet(memberResponse);
            }
            case DELETE -> {
                UUID id = consoleIOHandler.commandMemberId();
                memberController.deleteById(id);
                consoleIOHandler.printSuccessDelete();
            }
            default ->{
                throw new IncorrectInputException("function", function, "목록에 있는 것들 중 선택하세요.");
            }
        }
    }

    private void walletMode(){
        String function = consoleIOHandler.printSelectWalletFunction();
        switch (function){
            case CREATE -> {
                consoleIOHandler.printSuccessGetMemberList(memberController.findAll());
                UUID memberId = consoleIOHandler.commandMemberId();
                consoleIOHandler.printSuccessGetVoucherList(voucherController.findAll());
                UUID voucherId = consoleIOHandler.commandVoucherId();
                walletController.walletSave(memberId, voucherId);
            }
            case FINDMEMBER -> {
                consoleIOHandler.printSuccessGetVoucherList(voucherController.findAll());
                UUID voucherId = consoleIOHandler.commandVoucherId();
                List<MemberResponse> memberList = walletController.findByVoucherId(voucherId);
                consoleIOHandler.printSuccessGetMemberList(memberList);
            }
            case FINDVOUCHER -> {
                consoleIOHandler.printSuccessGetMemberList(memberController.findAll());
                UUID memberId = consoleIOHandler.commandMemberId();
                List<VoucherResponse> voucherList = walletController.findByMemberId(memberId);
                consoleIOHandler.printSuccessGetVoucherList(voucherList);
            }
            case DELETE -> {
                consoleIOHandler.printSuccessGetMemberList(memberController.findAll());
                UUID memberId = consoleIOHandler.commandMemberId();
                consoleIOHandler.printSuccessGetVoucherList(voucherController.findAll());
                UUID voucherId = consoleIOHandler.commandVoucherId();
                walletController.deleteById(memberId, voucherId);
            }
            default -> {
                throw new IncorrectInputException("function", function, "목록에 있는 것들 중 선택하세요.");
            }
        }
    }

}
