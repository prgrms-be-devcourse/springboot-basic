package com.prgms.VoucherApp.view.console;

import com.prgms.VoucherApp.domain.Voucher;
import com.prgms.VoucherApp.domain.VoucherPolicy;
import com.prgms.VoucherApp.view.Output;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

import java.awt.*;
import java.util.List;

public class ConsoleOutputView implements Output {

    private final TextTerminal<?> textTerminal;

    public ConsoleOutputView() {
        TextIO textIO = TextIoFactory.getTextIO();
        textTerminal = textIO.getTextTerminal();
    }

    @Override
    public void outputDisplayMenu() {
        printProgram();
        printExitMenuCommand();
        printCreateMenuCommand();
        printListMenuCommand();
    }


    public void printProgram() {
        textTerminal.println("=== Voucher Program ===");
    }

    public void printExitMenuCommand() {
        textTerminal.print("Type ");
        textTerminal.executeWithPropertiesConfigurator(terminalProperties -> {
            terminalProperties.setPromptBold(true);
            terminalProperties.setPromptColor(Color.red);
        }, text -> text.print("exit "));
        textTerminal.print("to ");
        textTerminal.executeWithPropertiesConfigurator(terminalProperties -> {
            terminalProperties.setPromptColor(Color.red);
        }, text -> text.print("exit "));
        textTerminal.println("the program.");
    }

    public void printCreateMenuCommand() {
        textTerminal.print("Type ");
        textTerminal.executeWithPropertiesConfigurator(terminalProperties -> {
            terminalProperties.setPromptBold(true);
        }, text -> text.print("create "));
        textTerminal.println("to create a new voucher.");
    }

    public void printListMenuCommand() {
        textTerminal.print("Type ");
        textTerminal.executeWithPropertiesConfigurator(terminalProperties -> {
            terminalProperties.setPromptBold(true);
        }, text -> text.print("list "));
        textTerminal.println("to list all vouchers.");
    }

    @Override
    public void outputDisplayVoucherPolicy() {
        textTerminal.println("=== Voucher Create ===");
        for (VoucherPolicy policy : VoucherPolicy.values()) {
            textTerminal.print("Type ");
            textTerminal.executeWithPropertiesConfigurator(terminalProperties -> {
                terminalProperties.setPromptBold(true);
            }, text -> text.print(policy.getVoucherPolicy()));
            textTerminal.println(", description : " + policy.getPolicyDescription());
        }
    }

    @Override
    public void outputDisplayDiscountCondition(VoucherPolicy policy) {
        textTerminal.executeWithPropertiesConfigurator(terminalProperties -> {
            terminalProperties.setPromptBold(true);
            terminalProperties.setPromptColor(Color.red);
        }, text -> text.println(policy.getDiscountCondition()));
    }

    @Override
    public void outputCreatedMsg(Voucher voucher) {
        textTerminal.println(voucher + "할인권이 생성되었습니다.");
    }

    @Override
    public void outputNotCreatedMsg() {
        textTerminal.println("알 수 없는 오류로 할인권이 생성되지 않았습니다.");
    }

    @Override
    public void outputVoucherHistory(List<Voucher> voucher) {

    }
}
