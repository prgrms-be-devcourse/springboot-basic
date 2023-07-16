package com.tangerine.voucher_system.application.global.io;

import com.tangerine.voucher_system.application.customer.controller.CustomerMenu;
import com.tangerine.voucher_system.application.global.model.CommandMenu;
import com.tangerine.voucher_system.application.voucher.controller.VoucherMenu;
import com.tangerine.voucher_system.application.voucher.model.DiscountValue;
import com.tangerine.voucher_system.application.voucher.model.VoucherType;
import com.tangerine.voucher_system.application.wallet.controller.WalletMenu;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class InputConsole {

    private static final TextIO textIO = TextIoFactory.getTextIO();

    public CommandMenu readCommandMenu() {
        String input = textIO.newStringInputReader()
                .withInputTrimming(true)
                .read("CommandMenu Selection: ");
        return CommandMenu.getCommandMenu(input);
    }

    public CustomerMenu readCustomerMenu() {
        return textIO.newEnumInputReader(CustomerMenu.class)
                .read("CustomerMenu Selection: ");
    }

    public VoucherMenu readVoucherMenu() {
        return textIO.newEnumInputReader(VoucherMenu.class)
                .read("VoucherMenu Selection: ");
    }

    public WalletMenu readWalletMenu() {
        return textIO.newEnumInputReader(WalletMenu.class)
                .read("WalletMenu Selection: ");
    }

    public VoucherType readVoucherType() {
        String input = textIO.newStringInputReader()
                .withInputTrimming(true)
                .read("Voucher Type Selection: ");
        return VoucherType.getVoucherType(input);
    }

    public DiscountValue readDiscountValue(VoucherType voucherType) {
        String inputDiscountValue = textIO.newStringInputReader()
                .withInputTrimming(true)
                .read("Discount Value: ");
        return new DiscountValue(voucherType, inputDiscountValue);
    }

    public UUID readId() {
        String input = textIO.newStringInputReader()
                .withInputTrimming(true)
                .read("Id: ");
        return UUID.fromString(input);
    }

    public String readName() {
        return textIO.newStringInputReader()
                .withInputTrimming(true)
                .read("Name: ");
    }

    public boolean readBlack() {
        return textIO.newBooleanInputReader()
                .read("Black Customer: ");
    }

}
