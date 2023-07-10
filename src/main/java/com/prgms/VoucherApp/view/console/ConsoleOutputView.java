package com.prgms.VoucherApp.view.console;

import com.prgms.VoucherApp.domain.customer.dto.CustomerResponse;
import com.prgms.VoucherApp.domain.customer.dto.CustomersResponse;
import com.prgms.VoucherApp.domain.voucher.dto.VoucherResDto;
import com.prgms.VoucherApp.domain.voucher.model.VoucherType;
import com.prgms.VoucherApp.view.CustomerCommand;
import com.prgms.VoucherApp.view.ManagementType;
import com.prgms.VoucherApp.view.Output;
import com.prgms.VoucherApp.view.VoucherCommand;
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
    public void printManagementMenu() {
        textTerminal.println("=== Program ===");

        for (ManagementType managementType : ManagementType.values()) {
            textTerminal.print("Type ");
            textTerminal.executeWithPropertiesConfigurator(terminalProperties -> {
                terminalProperties.setPromptBold(true);
            }, text -> text.print(managementType.getTypeNumber() + " "));
            textTerminal.print("execute ");
            textTerminal.executeWithPropertiesConfigurator(terminalProperties -> {
                terminalProperties.setPromptBold(true);
            }, text -> text.print(managementType.getTypeName() + " Management "));
            textTerminal.print("Program");
            textTerminal.println();
        }
    }

    @Override
    public void printCustomerCommand() {
        textTerminal.println("=== Customer management Program ===");

        for (CustomerCommand customerCommand : CustomerCommand.values()) {
            textTerminal.print("Type ");
            textTerminal.executeWithPropertiesConfigurator(terminalProperties -> {
                terminalProperties.setPromptBold(true);
            }, text -> text.print(customerCommand.getCustomerCommandNumber() + " "));
            textTerminal.print("execute ");
            textTerminal.executeWithPropertiesConfigurator(terminalProperties -> {
                terminalProperties.setPromptBold(true);
            }, text -> text.print(customerCommand.getCustomerCommandName() + " Command"));
            textTerminal.println();
        }
    }

    @Override
    public void printVoucherCommand() {
        textTerminal.println("=== Voucher management Program ===");

        for (VoucherCommand voucherCommand : VoucherCommand.values()) {
            textTerminal.print("Type ");
            textTerminal.executeWithPropertiesConfigurator(terminalProperties -> {
                terminalProperties.setPromptBold(true);
            }, text -> text.print(voucherCommand.getVoucherCommandNumber() + " "));
            textTerminal.print("execute ");
            textTerminal.executeWithPropertiesConfigurator(terminalProperties -> {
                terminalProperties.setPromptBold(true);
            }, text -> text.print(voucherCommand.getVoucherCommandName() + " Command"));
            textTerminal.println();
        }
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
    public void printErrorMsg(String msg) {
        textTerminal.println(msg);
    }

    @Override
    public void printVoucherList(List<VoucherResDto> findVouchers) {
        if (findVouchers.isEmpty()) {
            log.error("The user tried to view the list, but currently, the list is empty");
            textTerminal.println("There are no available discount vouchers stored.");
            return;
        }
        findVouchers.forEach((voucher -> textTerminal.println(voucher.getVoucherInfo())));
    }

    @Override
    public void printVoucher(VoucherResDto voucher) {
        textTerminal.println(voucher.getVoucherInfo());
    }

    @Override
    public void printBlackLists(CustomersResponse blacklists) {
        if (blacklists.isEmpty()) {
            log.error("The user tried to view the blacklist, but currently, the list is empty");
            textTerminal.println("There are no blacklisted entries currently registered.");
            return;
        }

        blacklists.getCustomers()
            .forEach((blackList -> textTerminal.println(String.valueOf(blackList))));
    }

    @Override
    public void printCustomers(CustomersResponse customers) {
        if (customers.isEmpty()) {
            textTerminal.println("There are no blacklisted entries currently registered.");
            return;
        }

        customers.getCustomers()
            .forEach((customer) -> textTerminal.println(String.valueOf(customer)));
    }

    @Override
    public void printCustomer(CustomerResponse customer) {
        textTerminal.println(String.valueOf(customer));
    }

    @Override
    public void printFindEmpty() {
        textTerminal.println("존재하지 않는 ID가 입력되었습니다.");
    }

    @Override
    public void printNotImplementMsg() {
        textTerminal.println("아직 미 완성된 기능입니다.");
    }
}
