package com.weeklyMission.client;

import com.weeklyMission.console.ConsoleIO;
import com.weeklyMission.controller.VoucherController;
import com.weeklyMission.domain.Voucher;
import com.weeklyMission.dto.VoucherResponse;
import com.weeklyMission.exception.IncorrectInputException;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class Client {

    private final ConsoleIO consoleIOHandler;
    private final VoucherController voucherController;

    public Client(ConsoleIO consoleIOHandler, VoucherController voucherController) {
        this.consoleIOHandler = consoleIOHandler;
        this.voucherController = voucherController;
    }

    public void run() {
        String mode = consoleIOHandler.printSelectMode();
        switch (mode) {
            case "voucher" -> {
                voucherMode();
            }
            case "member" -> {
                memberMode();
            }
            case "exit" -> {
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
            case "create" -> {
                VoucherType voucherType = VoucherType.of(consoleIOHandler.printSelectVoucherType());
                Voucher voucher = voucherType.giveVoucher(consoleIOHandler);
                VoucherResponse voucherDto = voucherController.create(voucher);
                consoleIOHandler.printSuccessCreate(voucherDto);
            }
            case "list" -> {
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
    }

}
