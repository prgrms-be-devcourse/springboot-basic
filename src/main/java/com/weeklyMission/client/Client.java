package com.weeklyMission.client;

import com.weeklyMission.common.CommandType;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Client {

    private final Logger logger = LoggerFactory.getLogger(Client.class);
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
            case CommandType.VOUCHER-> {
                voucherMode();
            }
            case CommandType.MEMBER -> {
                memberMode();
            }
            case CommandType.WALLET -> {
                walletMode();
            }
            case CommandType.EXIT -> {
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
            case CommandType.CREATE -> {
                VoucherType voucherType = VoucherType.of(consoleIOHandler.printSelectVoucherType());
                UUID uuid = UUID.randomUUID();
                VoucherRequest voucherRequest = new VoucherRequest(voucherType.getType(),
                    uuid.toString(), consoleIOHandler.printAmountCommand());
                logger.info("UUID -> {}", uuid);
                logger.info("toString -> {}", uuid.toString());

                voucherController.create(voucherRequest);
                consoleIOHandler.printSuccessCreate();
            }
            case CommandType.LIST -> {
                List<VoucherResponse> voucherListDto = voucherController.findAll();
                consoleIOHandler.printSuccessGetVoucherList(voucherListDto);
            }
            case CommandType.FIND -> {
                String id = consoleIOHandler.commandVoucherId();
                VoucherResponse voucherResponse = voucherController.findById(id);
                consoleIOHandler.printSuccessGet(voucherResponse);
            }
            case CommandType.DELETE -> {
                String id = consoleIOHandler.commandVoucherId();
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
            case CommandType.CREATE -> {
                MemberRequest memberRequest = new MemberRequest(UUID.randomUUID().toString(), consoleIOHandler.nameCommand(), consoleIOHandler.emailCommand(), consoleIOHandler.ageCommand());
                memberController.create(memberRequest);
                consoleIOHandler.printSuccessCreate();
            }
            case CommandType.LIST ->{
                List<MemberResponse> memberList = memberController.findAll();
                consoleIOHandler.printSuccessGetMemberList(memberList);
            }
            case CommandType.FIND -> {
                String id = consoleIOHandler.commandMemberId();
                MemberResponse memberResponse = memberController.findById(id);
                consoleIOHandler.printSuccessGet(memberResponse);
            }
            case CommandType.DELETE -> {
                String id = consoleIOHandler.commandMemberId();
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
            case CommandType.CREATE -> {
                consoleIOHandler.printSuccessGetMemberList(memberController.findAll());
                String memberId = consoleIOHandler.commandMemberId();
                consoleIOHandler.printSuccessGetVoucherList(voucherController.findAll());
                String voucherId = consoleIOHandler.commandVoucherId();
                walletController.walletSave(memberId, voucherId);
            }
            case CommandType.FINDMEMBER -> {
                consoleIOHandler.printSuccessGetVoucherList(voucherController.findAll());
                String voucherId = consoleIOHandler.commandVoucherId();
                List<MemberResponse> memberList = walletController.findByVoucherId(voucherId);
                consoleIOHandler.printSuccessGetMemberList(memberList);
            }
            case CommandType.FINDVOUCHER -> {
                consoleIOHandler.printSuccessGetMemberList(memberController.findAll());
                String memberId = consoleIOHandler.commandMemberId();
                List<VoucherResponse> voucherList = walletController.findByMemberId(memberId);
                consoleIOHandler.printSuccessGetVoucherList(voucherList);
            }
            case CommandType.DELETE -> {
                consoleIOHandler.printSuccessGetMemberList(memberController.findAll());
                String memberId = consoleIOHandler.commandMemberId();
                consoleIOHandler.printSuccessGetVoucherList(voucherController.findAll());
                String voucherId = consoleIOHandler.commandVoucherId();
                walletController.deleteById(memberId, voucherId);
            }
            default -> {
                throw new IncorrectInputException("function", function, "목록에 있는 것들 중 선택하세요.");
            }
        }
    }

}
