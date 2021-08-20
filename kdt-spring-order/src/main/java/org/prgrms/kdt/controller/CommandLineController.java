package org.prgrms.kdt.controller;

import org.prgrms.kdt.day2_work.CommandLineInput;
import org.prgrms.kdt.day2_work.CommandType;
import org.prgrms.kdt.day2_work.Manual;
import org.prgrms.kdt.service.VoucherService;
import org.prgrms.kdt.service.VoucherType;
import org.prgrms.kdt.voucher.Voucher;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Controller
public class CommandLineController {

    private final VoucherService voucherService;

    public CommandLineController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public void startProgram() throws IOException {
        while (true) {
            Manual.startProgram();
            doCommand(chooseCommand());
        }
    }

    public CommandType chooseCommand() throws IOException {
        return CommandLineInput.inputCommand();
    }

    public void doCommand(CommandType command) throws IOException {
        if (command == CommandType.CREATE) {
            createCommand();
            return;
        }

        if (command == CommandType.LIST){
            listCommand();
            return;
        }

        if (command == CommandType.EXIT)
            exitCommand();
    }

    public void createCommand() throws IOException {
        Manual.chooseType();
        VoucherType type = CommandLineInput.inputType();
        String amount =  CommandLineInput.inputAmount(type);
        voucherService.createVoucher(type, amount);
    }

    public void exitCommand() throws IOException {
        Manual.exit();
        CommandLineInput.closeReader();
        System.exit(0);
    }

    public void listCommand(){
        if(voucherService.getVoucherList().isEmpty())
            Manual.listIsEmpty();
        else{
            Map<UUID, Voucher> voucherList = voucherService.getVoucherList();
            Manual.showList(voucherList);
        }
    }
}
