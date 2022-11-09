package com.prgrms.springbootbasic.app;

import com.prgrms.springbootbasic.console.Console;
import com.prgrms.springbootbasic.handler.menu.CommandType;
import com.prgrms.springbootbasic.handler.menu.MenuHandler;
import com.prgrms.springbootbasic.handler.menu.dto.MenuInputResult;
import com.prgrms.springbootbasic.handler.vocuher.VoucherType;
import com.prgrms.springbootbasic.handler.vocuher.dto.VoucherInfo;
import org.springframework.stereotype.Component;

@Component
public class VoucherApplication {

    //얘네들을 enum화 한 다음, CommandType, VoucherType에 필드 추가(message)
    private static final String COMMAND_NOT_SUPPORTER = "Command not supported yet.";
    private static final String MENU = "=== Voucher Program ===\n" +
            "Type **exit** to exit the program.\n" +
            "Type **create** to create a new voucher.\n" +
            "Type **list** to list all vouchers.";
    private static final String EXIT_MESSAGE = "Exit program. Bye.";
    private static final String VOUCHER_TYPE_MESSAGE = "Type f for fixed amount, or type p for percent";
    private final MenuHandler menuHandler;
    private final ApplicationStatus applicationStatus;
    private final Console console;

    public VoucherApplication(MenuHandler menuHandler, ApplicationStatus applicationStatus, Console console) {
        this.menuHandler = menuHandler;
        this.applicationStatus = applicationStatus;
        this.console = console;
    }

    public void runLifecycle() {
        while (applicationStatus.isRunning()) {
            getCommand();
        }
    }

    private void getCommand() {
        console.printMessage(MENU);
        MenuInputResult inputResult = console.getCommand();
        try {
            CommandType commandType = CommandType.findByCommand(inputResult.getCommand());
            controlMenu(commandType);
        } catch (IllegalArgumentException e) {
            console.printMessage(e.getMessage());
        }
    }

    private void controlMenu(CommandType command) {
        switch (command) {
            case EXIT -> {
                applicationStatus.exit();
                console.printMessage(EXIT_MESSAGE);
            }
            case CREATE -> createVoucher();
            case LIST -> menuHandler.list();
            default -> console.printMessage(COMMAND_NOT_SUPPORTER);
        }
    }

    //get~~으로 시작하는게 뭔가 맘에 안든단 말이지...
    private void createVoucher(){
        try{
            VoucherType voucherType = getVoucherType();
            int amount = getAmount(voucherType);
            menuHandler.create(new VoucherInfo(voucherType, amount));
        } catch(IllegalArgumentException e){
            console.printMessage(e.getMessage());
        }
    }

    private VoucherType getVoucherType(){
        console.printMessage(VOUCHER_TYPE_MESSAGE);
        String voucherTypeInput = console.getInput();
        return VoucherType.findByInputValue(voucherTypeInput);
    }

    private int getAmount(VoucherType voucherType){
        console.printMessage(voucherType.getTypingMessage());
        String amountInput = console.getInput();
        int amount = voucherType.validateAmount(amountInput);
        return amount;
    }
}
