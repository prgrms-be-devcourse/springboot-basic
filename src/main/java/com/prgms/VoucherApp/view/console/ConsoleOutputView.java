package com.prgms.VoucherApp.view.console;

import com.prgms.VoucherApp.domain.Voucher;
import com.prgms.VoucherApp.domain.VoucherType;
import com.prgms.VoucherApp.dto.VoucherDto;
import com.prgms.VoucherApp.view.Output;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.List;

public class ConsoleOutputView implements Output {

    private static final Logger log = LoggerFactory.getLogger(ConsoleOutputView.class);
    private final TextTerminal<?> textTerminal;

    public ConsoleOutputView() {
        TextIO textIO = TextIoFactory.getTextIO();
        textTerminal = textIO.getTextTerminal();
    }

    @Override
    public void printDisplayMenu() {
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
    public void printDisplayVoucherPolicy() {
        textTerminal.println("=== Voucher Create ===");
        for (VoucherType voucherType : VoucherType.values()) {
            textTerminal.print("Type ");
            textTerminal.executeWithPropertiesConfigurator(terminalProperties -> {
                terminalProperties.setPromptBold(true);
            }, text -> {
                switch (voucherType) {
                    case FIXED_VOUCHER -> {
                        text.print(voucherType.getVoucherPolicy());
                        text.println(" to create " + "a Fixed Amount Voucher.");
                    }
                    case PERCENT_VOUCHER -> {
                        text.print(voucherType.getVoucherPolicy());
                        text.println(" to create " + "a Percent Voucher.");
                    }
                }
            });
        }
    }

    @Override
    public void printDisplayDiscountCondition(VoucherType voucherType) {
        textTerminal.executeWithPropertiesConfigurator(terminalProperties -> {
            terminalProperties.setPromptBold(true);
            terminalProperties.setPromptColor(Color.red);
        }, text -> {
            switch (voucherType) {
                case FIXED_VOUCHER -> {
                    text.println("0 이상으로 입력해주세요.");
                }
                case PERCENT_VOUCHER -> {
                    text.println("0 이상 100 이하로 입력해주세요.");
                }
            }
        });
    }

    @Override
    public void printCreatedMsg(Voucher voucher) {
        log.info("The discount coupon {} was created successfully.", voucher);
        textTerminal.println(voucher + "할인권이 생성되었습니다.");
    }

    @Override
    public void printVoucherList(List<VoucherDto> voucherDtos) {
        if (voucherDtos.isEmpty()) {
            log.error("The user tried to view the list, but currently, the list is empty");
            textTerminal.println("저장되어있는 할인권이 없습니다.");
            return;
        }
        voucherDtos.forEach((voucher -> textTerminal.println(voucher.getVoucherInfo())));
    }
}
