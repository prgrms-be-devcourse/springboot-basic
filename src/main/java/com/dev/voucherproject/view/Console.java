package com.dev.voucherproject.view;

import com.dev.voucherproject.model.customer.CustomerDto;
import com.dev.voucherproject.model.menu.VoucherMenu;
import com.dev.voucherproject.model.voucher.VoucherDto;
import com.dev.voucherproject.model.voucher.VoucherPolicy;
import com.dev.voucherproject.model.menu.Menu;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
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
        textTerminal.executeWithPropertiesConfigurator(props ->
                props.setPromptBold(true), t -> t.print(Menu.EXIT.getMenuName()));
        textTerminal.println(" to exit the program.");

        textTerminal.print("Type ");
        textTerminal.executeWithPropertiesConfigurator(props ->
                props.setPromptBold(true), t -> t.print(Menu.Voucher.getMenuName()));
        textTerminal.println(" to voucher view voucher services.");

        textTerminal.print("Type ");
        textTerminal.executeWithPropertiesConfigurator(props ->
                props.setPromptBold(true), t -> t.print(Menu.BLACKLIST.getMenuName()));
        textTerminal.println(" to list all blacklist.");
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

    public void printVoucherMenu() {
        textTerminal.println();
        textTerminal.println("Type 1 to create a new voucher.");
        textTerminal.println("Type 2 to list all vouchers.");
        textTerminal.println("Type 3 to find specific voucher.");
        textTerminal.println("Type 4 to delete all vouchers.");
        textTerminal.println("Type 5 to delete specific vouchers.");
        textTerminal.println("Type 6 to update specific vouchers.");
        textTerminal.println("Type 7 to main menu.");
    }


    public void printAllVouchers(List<VoucherDto> dtos) {
        dtos.forEach(this::printVoucher);
        textTerminal.println();
    }


    public void printVoucher(VoucherDto dto) {
        textTerminal.println("[%s, %d] %s".formatted(dto.getVoucherPolicy().name(), dto.getDiscountNumber(), dto.getVoucherId().toString()));
    }

    public void printAllCustomers(List<CustomerDto> dtos) {
        dtos.forEach(this::printCustomer);
        textTerminal.println();
    }
    public void printCustomer(CustomerDto dto) {
        textTerminal.println("[id, name] %s, %s".formatted(dto.getCustomerId(), dto.getName()));
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
                .withInlinePossibleValues(Arrays.stream(Menu.values())
                        .map(Menu::getMenuName)
                        .toList())
                .read(">>");
    }

    public String inputVoucherPolicySelection() {
        return textIO.newStringInputReader()
                .withInputTrimming(true)
                .withInlinePossibleValues(Arrays.stream(VoucherPolicy.values())
                        .map(VoucherPolicy::getPolicyName)
                        .toList())
                .read(">>");
    }
    public String inputVoucherMenuSelection() {
        return textIO.newStringInputReader()
            .withInputTrimming(true)
            .withInlinePossibleValues(Arrays.stream(VoucherMenu.values())
                .map(VoucherMenu::getVoucherMenuName)
                .toList())
            .read(">>");
    }

    public String inputUuid() {
        return textIO.newStringInputReader()
            .withInputTrimming(true)
            .read(">> UUID: ");
    }

    public void newLine() {
        textTerminal.println();
    }
}
