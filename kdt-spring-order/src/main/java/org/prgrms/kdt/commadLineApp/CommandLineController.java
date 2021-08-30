package org.prgrms.kdt.commadLineApp;

import org.prgrms.kdt.voucher.VoucherService;
import org.prgrms.kdt.voucher.VoucherType;
import org.prgrms.kdt.voucher.Voucher;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Controller
public class CommandLineController {

    private final VoucherService voucherService;

    public CommandLineController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public void startProgram(File blackList) throws IOException {
        while (true) {
            InPutView.startProgram();
            doCommand(chooseCommand(), blackList);
        }
    }

    public CommandType chooseCommand() throws IOException {
        return CommandLineInput.inputCommand();
    }

    public void doCommand(CommandType command, File blackList) throws IOException {
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

        if (command == CommandType.BLACKLIST)
            blackListCommand(blackList);
    }

    private void blackListCommand(File blackList) throws IOException {
        OutPutView.showBlackList(blackList);
    }

    public void createCommand() throws IOException {
        InPutView.chooseType();
        VoucherType type = CommandLineInput.inputType();
        String amount =  CommandLineInput.inputAmount(type);
        voucherService.createVoucher(type, amount);
    }

    public void exitCommand() throws IOException {
        InPutView.exit();
        CommandLineInput.closeReader();
        System.exit(0);
    }

    public void listCommand(){
        if(voucherService.getVoucherList().isEmpty())
            InPutView.listIsEmpty();
        else{
            Map<UUID, Voucher> voucherList = voucherService.getVoucherList();
            InPutView.showList(voucherList);
        }
    }
}
