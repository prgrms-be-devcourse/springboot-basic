package com.tangerine.voucher_system.application.global.io;

import com.tangerine.voucher_system.application.customer.controller.CustomerDto;
import com.tangerine.voucher_system.application.customer.controller.CustomerMenu;
import com.tangerine.voucher_system.application.customer.model.Name;
import com.tangerine.voucher_system.application.global.model.CommandMenu;
import com.tangerine.voucher_system.application.voucher.controller.VoucherDto;
import com.tangerine.voucher_system.application.voucher.controller.VoucherMenu;
import com.tangerine.voucher_system.application.voucher.model.DiscountValue;
import com.tangerine.voucher_system.application.voucher.model.VoucherType;
import com.tangerine.voucher_system.application.wallet.controller.WalletMenu;
import com.tangerine.voucher_system.application.wallet.model.Wallet;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Component
public class ConsoleManager {

    private final InputConsole inputConsole;
    private final OutputConsole outputConsole;

    public ConsoleManager(InputConsole inputConsole, OutputConsole outputConsole) {
        this.inputConsole = inputConsole;
        this.outputConsole = outputConsole;
    }

    public void consoleError(Exception e) {
        outputConsole.printError(e);
    }

    public void consoleClosePlatform() {
        outputConsole.closePlatform();
    }

    public CommandMenu consoleCommandMenu() {
        outputConsole.showCommandMenu();
        return inputConsole.readCommandMenu();
    }

    public CustomerMenu consoleCustomerMenu() {
        outputConsole.showCustomerMenu();
        return inputConsole.readCustomerMenu();
    }

    public VoucherMenu consoleVoucherMenu() {
        outputConsole.showVoucherMenu();
        return inputConsole.readVoucherMenu();
    }

    public WalletMenu consoleWalletMenu() {
        outputConsole.showWalletMenu();
        return inputConsole.readWalletMenu();
    }

    public VoucherType consoleVoucherType() {
        outputConsole.showVoucherType();
        return inputConsole.readVoucherType();
    }

    private DiscountValue consoleDiscountValue(VoucherType voucherType) {
        return inputConsole.readDiscountValue(voucherType);
    }

    public VoucherDto consoleVoucherDto() {
        UUID voucherId = UUID.randomUUID();
        VoucherType voucherType = consoleVoucherType();
        DiscountValue discountValue = consoleDiscountValue(voucherType);
        LocalDate createdAt = LocalDate.now();
        return new VoucherDto(voucherId, voucherType, discountValue, createdAt);
    }

    public UUID consoleId() {
        return inputConsole.readId();
    }

    public Name consoleName() {
        return inputConsole.readName();
    }

    private boolean consoleBlack() {
        return inputConsole.readBlack();
    }

    public CustomerDto consoleCustomerDto() {
        UUID id = UUID.randomUUID();
        Name name = consoleName();
        boolean isBlack = consoleBlack();
        return new CustomerDto(id, name, isBlack);
    }

    public Wallet consoleWalletDto() {
        UUID walletId = UUID.randomUUID();
        UUID voucherId = consoleId();
        UUID customerId = consoleId();
        return new Wallet(walletId, voucherId, customerId);
    }

    public void printVoucherDto(VoucherDto voucherDto) {
        outputConsole.printMessage(voucherDto.toString());
    }

    public void printCustomerDto(CustomerDto customerDto) {
        outputConsole.printMessage(customerDto.toString());
    }

    public void printVoucherList(List<VoucherDto> list) {
        outputConsole.printMessage(OutputMessage.LIST_VOUCHERS.getMessageText());
        outputConsole.printList(
                list.stream()
                        .map(VoucherDto::toString)
                        .toList()
        );
    }

    public void printCustomerList(List<CustomerDto> list) {
        outputConsole.printMessage(OutputMessage.LIST_CUSTOMERS.getMessageText());
        outputConsole.printList(
                list.stream()
                        .map(CustomerDto::toString)
                        .toList()
        );
    }
}
