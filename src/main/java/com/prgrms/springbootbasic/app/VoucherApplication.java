package com.prgrms.springbootbasic.app;

import static com.prgrms.springbootbasic.common.exception.ExceptionMessage.VOUCHER_NOT_SUPPORTED;

import com.prgrms.springbootbasic.common.exception.*;
import com.prgrms.springbootbasic.console.Console;
import com.prgrms.springbootbasic.customer.BlacklistManager;
import com.prgrms.springbootbasic.customer.domain.Customer;
import com.prgrms.springbootbasic.voucher.domain.Voucher;
import com.prgrms.springbootbasic.voucher.VoucherManager;
import com.prgrms.springbootbasic.voucher.VoucherType;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("dev")
@Component
public class VoucherApplication {

    private static final Logger logger = LoggerFactory.getLogger(VoucherApplication.class);

    private final VoucherManager voucherManager;
    private final BlacklistManager blacklistManager;
    private final Console console;

    private ApplicationStatus applicationStatus;

    public VoucherApplication(VoucherManager voucherManager, BlacklistManager blacklistManager, Console console) {
        this.voucherManager = voucherManager;
        this.blacklistManager = blacklistManager;
        this.console = console;
    }

    public void runLifecycle() {
        applicationStatus = new ApplicationStatus();
        logger.info("Voucher application started successfully.");
        while (applicationStatus.isRunning()) {
            getCommand();
        }
    }

    private void getCommand() {
        console.printVoucherMenu();
        String inputCommand = console.getCommand();
        try {
            VoucherCommandType voucherCommandType = VoucherCommandType.from(inputCommand);
            controlMenu(voucherCommandType);
        } catch (InvalidCommandTypeException e) {
            console.printExceptionMessage(e.getMessage());
        }
    }

    private void controlMenu(VoucherCommandType command) {
        switch (command) {
            case EXIT -> exit();
            case CREATE -> create();
            case LIST -> list();
            case BLACKLIST -> blacklist();
            default -> console.printCommendNotSupported();
        }
    }

    private void blacklist() {
        try {
            List<Customer> blacklist = blacklistManager.list();
            console.printBlackList(blacklist);
            logger.info("List up all blacked users.");
        } catch (FileIOException | FileNotExistException | FileFormatException e) {
            logger.error(e.getMessage(), e);
            console.printExceptionMessage(e.getMessage());
            exit();
        }
    }

    private void exit() {
        applicationStatus.exit();
        logger.info("Exit Voucher application.");
    }

    private void list() {
        try {
            List<Voucher> vouchers = voucherManager.list();
            console.printVoucherList(vouchers);
            logger.info("List up all Vouchers.");
        } catch (FileIOException | FileNotExistException | FileFormatException e) {
            logger.error(e.getMessage(), e);
            console.printExceptionMessage(e.getMessage());
            exit();
        }
    }

    private void create() {
        try {
            VoucherType voucherType = getVoucherType();
            String amountInput = getAmount(voucherType);
            voucherManager.create(voucherType, amountInput);
            console.printCreateSuccessMessage();
            logger.info("New Voucher created.");
        } catch (InvalidVoucherTypeException | NumberFormatException | AmountOutOfBoundException e) {
            console.printExceptionMessage(e.getMessage());
        } catch (FileIOException | FileNotExistException | FileFormatException e) {
            logger.error(e.getMessage(), e);
            console.printExceptionMessage(e.getMessage());
            exit();
        }
    }

    private VoucherType getVoucherType() {
        console.printChoosingVoucher();
        String voucherTypeInput = console.getInput();
        VoucherType voucherType = VoucherType.fromInputValue(voucherTypeInput);
        return voucherType;
    }

    private String getAmount(VoucherType voucherType) {
        console.printDiscountAmountMessage(voucherType);
        return console.getInput();
    }
}
