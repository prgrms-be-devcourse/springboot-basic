package com.prgrms.springbootbasic.app;

import com.prgrms.springbootbasic.common.exception.AmountOutOfBoundException;
import com.prgrms.springbootbasic.common.exception.HaveNoVoucherException;
import com.prgrms.springbootbasic.common.exception.InvalidVoucherTypeException;
import com.prgrms.springbootbasic.console.Console;
import com.prgrms.springbootbasic.voucher.dto.VoucherResponse;
import com.prgrms.springbootbasic.voucher.VoucherManager;
import com.prgrms.springbootbasic.voucher.VoucherType;
import com.prgrms.springbootbasic.voucher.dto.VoucherInfo;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class VoucherApplication {

    private final VoucherManager voucherManager;

    private final ApplicationStatus applicationStatus;

    private final Console console;

    public VoucherApplication(VoucherManager voucherManager, ApplicationStatus applicationStatus,
                              Console console) {
        this.voucherManager = voucherManager;
        this.applicationStatus = applicationStatus;
        this.console = console;
    }

    public void runLifecycle() {
        while (applicationStatus.isRunning()) {
            getCommand();
        }
    }

    private void getCommand() {
        console.printMenu();
        String inputCommand = console.getCommand();
        try {
            CommandType commandType = CommandType.findByCommand(inputCommand);
            controlMenu(commandType);
        } catch (IllegalArgumentException e) {
            console.printExceptionMessage(e.getMessage());
        }
    }

    private void controlMenu(CommandType command) {
        switch (command) {
            case EXIT -> {
                applicationStatus.exit();
                console.printExitMessage();
            }
            case CREATE -> create();
            case LIST -> list();
            default -> console.printCommendNotSupported();
        }
    }

    private void list() {
        try {
            List<VoucherResponse> vouchers = voucherManager.list();
            vouchers.forEach(System.out::println);
        } catch (HaveNoVoucherException e) {
            console.printExceptionMessage(e.getMessage());
        }
    }

    private void create() {
        VoucherType voucherType = null;
        try {
            voucherType = getVoucherType();
            int amount = getAmount(voucherType);
            voucherManager.create(new VoucherInfo(voucherType, amount));
        } catch (InvalidVoucherTypeException | NumberFormatException e) {
            console.printExceptionMessage(e.getMessage());
        } catch (AmountOutOfBoundException e) {
            console.printAmountOutOfBoundMessage(voucherType, e.getMessage());
        }
    }

    private VoucherType getVoucherType() {
        console.printChoosingVoucher();
        String voucherTypeInput = console.getInput();
        return VoucherType.findByInputValue(voucherTypeInput);
    }

    private int getAmount(VoucherType voucherType) {
        console.printDiscountAmountMessage(voucherType);
        String amountInput = console.getInput();
        return voucherType.validateAmount(amountInput);
    }
}
