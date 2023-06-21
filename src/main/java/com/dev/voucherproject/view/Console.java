package com.dev.voucherproject.view;

import com.dev.voucherproject.model.Menu;
import com.dev.voucherproject.model.voucher.VoucherDto;
import com.dev.voucherproject.model.voucher.VoucherPolicy;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;
import org.springframework.stereotype.Component;

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
        textTerminal.executeWithPropertiesConfigurator(props -> props.setPromptBold(true), t -> t.print("exit"));
        textTerminal.println(" to exit the program.");

        textTerminal.print("Type ");
        textTerminal.executeWithPropertiesConfigurator(props -> props.setPromptBold(true), t -> t.print("create"));
        textTerminal.println(" to create a new voucher.");

        textTerminal.print("Type ");
        textTerminal.executeWithPropertiesConfigurator(props -> props.setPromptBold(true), t -> t.print("list"));
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


    public void printAllVoucherDtos(List<VoucherDto> dtos) {
        dtos.forEach(this::printVoucherDtos);
        textTerminal.println();
    }

    public void printVoucherDtos(VoucherDto dto) {
        textTerminal.printf("[%s, %d] %s\n", dto.getVoucherPolicy().name(), dto.getDiscountNumber(), dto.getUuid().toString());
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

    public Menu inputMenuSelection() {
        String input = textIO.newStringInputReader()
                .withInputTrimming(true)
                .withInlinePossibleValues("exit", "create", "list")
                .read(">>");
        return Menu.convertStringInputToMenu(input);
    }

    public VoucherPolicy inputVoucherPolicySelection() {
        String input = textIO.newStringInputReader()
                .withInputTrimming(true)
                .withInlinePossibleValues("fix", "per")
                .read(">>");
        return VoucherPolicy.convertStringInputToPolicy(input);
    }
}
