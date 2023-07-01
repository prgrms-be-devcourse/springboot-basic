package com.prgms.VoucherApp.view.console;

import com.prgms.VoucherApp.domain.customer.dto.CustomerDto;
import com.prgms.VoucherApp.domain.voucher.Voucher;
import com.prgms.VoucherApp.domain.voucher.VoucherType;
import com.prgms.VoucherApp.domain.voucher.dto.VoucherDto;
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
        printBlackListMenuCommand();
    }


    private void printProgram() {
        textTerminal.println("=== Voucher Program ===");
    }

    private void printExitMenuCommand() {
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

    private void printCreateMenuCommand() {
        textTerminal.print("Type ");
        textTerminal.executeWithPropertiesConfigurator(terminalProperties -> {
            terminalProperties.setPromptBold(true);
        }, text -> text.print("create "));
        textTerminal.println("to create a new voucher.");
    }

    private void printListMenuCommand() {
        textTerminal.print("Type ");
        textTerminal.executeWithPropertiesConfigurator(terminalProperties -> {
            terminalProperties.setPromptBold(true);
        }, text -> text.print("list "));
        textTerminal.println("to list all vouchers.");
    }

    private void printBlackListMenuCommand() {
        textTerminal.print("Type ");
        textTerminal.executeWithPropertiesConfigurator(terminalProperties -> {
            terminalProperties.setPromptBold(true);
        }, text -> text.print("blacklist "));
        textTerminal.println("to list all blacklist.");
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
                        text.print(voucherType.getVoucherTypeName());
                        text.println(" to create " + "a Fixed Amount Voucher.");
                    }
                    case PERCENT_VOUCHER -> {
                        text.print(voucherType.getVoucherTypeName());
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
                    text.println("Please enter a value greater than or equal to 0.");
                }
                case PERCENT_VOUCHER -> {
                    text.println("Please enter a value between 0 and 100");
                }
            }
        });
    }

    @Override
    public void printCreatedMsg(Voucher voucher) {
        VoucherDto voucherDto = new VoucherDto(voucher);
        log.info("The discount coupon {} was created successfully.", voucherDto.getVoucherInfo());
        textTerminal.println(voucherDto.getVoucherInfo() + " Voucher was created");
    }

    @Override
    public void printVoucherList(List<VoucherDto> voucherDtos) {
        if (voucherDtos.isEmpty()) {
            log.error("The user tried to view the list, but currently, the list is empty");
            textTerminal.println("There are no available discount vouchers stored.");
            return;
        }
        voucherDtos.forEach((voucher -> textTerminal.println(voucher.getVoucherInfo())));
    }

    @Override
    public void printBlackLists(List<CustomerDto> blackLists) {
        if (blackLists.isEmpty()) {
            log.error("The user tried to view the list, but currently, the list is empty");
            textTerminal.println("There are no blacklisted entries currently registered.");
            return;
        }

        blackLists.forEach((blackList -> textTerminal.println(blackList.getCustomerInfo())));
    }
}
