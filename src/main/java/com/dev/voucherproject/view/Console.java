package com.dev.voucherproject.view;

import com.dev.voucherproject.model.customer.CustomerDto;
import com.dev.voucherproject.model.voucher.VoucherDto;
import com.dev.voucherproject.model.voucher.VoucherPolicy;
import com.dev.voucherproject.model.menu.Menu;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;


@Component
public class Console {
    private final TextIO textIO;
    private final TextTerminal<?> textTerminal;

    public Console() {
        textIO = TextIoFactory.getTextIO();
        textTerminal = textIO.getTextTerminal();
        textTerminal.getProperties().setPromptColor("white");
    }

    public void printMenu() {
        textTerminal.println("=== Voucher Program ===");
        textTerminal.print("Type ");
        textTerminal.executeWithPropertiesConfigurator(props -> props.setPromptBold(true), t -> t.print(Menu.EXIT.getMenuName()));
        textTerminal.println(" to exit the program.");

        textTerminal.print("Type ");
        textTerminal.executeWithPropertiesConfigurator(props -> props.setPromptBold(true), t -> t.print(Menu.CREATE.getMenuName()));
        textTerminal.println(" to create a new voucher.");

        textTerminal.print("Type ");
        textTerminal.executeWithPropertiesConfigurator(props -> props.setPromptBold(true), t -> t.print(Menu.LIST.getMenuName()));
        textTerminal.println(" to list all vouchers.");

        textTerminal.print("Type ");
        textTerminal.executeWithPropertiesConfigurator(props -> props.setPromptBold(true), t -> t.print(Menu.BLACKLIST.getMenuName()));
        textTerminal.println(" to list all vouchers.");
    }

    public void printSelectVoucherPolicy() {
        textTerminal.println();
        textTerminal.println("Select FixAmountVoucher or PercentDiscountVoucher");
    }

    public void printFixAmountPolicyArgs() {
        textTerminal.println();
        textTerminal.println("Enter a discount amount");
    }

    public void printDiscountVoucherPolicyArgs() {
        textTerminal.println();
        textTerminal.println("Enter a percentage between 0 and 100.");
    }

    public void printAllVouchers(List<VoucherDto> dtos) {
        dtos.forEach(this::printVoucher);
        textTerminal.println();
    }

    public void printVoucher(VoucherDto dto) {
        textTerminal.println(dto.toString());
    }

    public void printAllCustomers(List<CustomerDto> dtos) {
        dtos.forEach(this::printCustomer);
        textTerminal.println();
    }
    public void printCustomer(CustomerDto dto) {
        textTerminal.println(dto.toString());
    }

    public long inputAmount() {
        return textIO.newLongInputReader()
                .withInputTrimming(true)
                .withMinVal(0L)
                .read(">>");
    }

    public long inputPercent() {
        return textIO.newLongInputReader()
                .withInputTrimming(true)
                .withMinVal(0L)
                .withMaxVal(100L)
                .read(">>");
    }

    public String inputMenuSelection() {
        return textIO.newStringInputReader()
                .withInputTrimming(true)
                .withInlinePossibleValues(Menu.getMenuNames())
                .read(">>");
    }

    public String inputVoucherPolicySelection() {
        return textIO.newStringInputReader()
                .withInputTrimming(true)
                .withInlinePossibleValues(VoucherPolicy.getPolicyNames())
                .read(">>");
    }

    public void newLine() {
        textTerminal.println();
    }
}
