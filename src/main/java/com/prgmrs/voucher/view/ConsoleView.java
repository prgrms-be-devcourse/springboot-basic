package com.prgmrs.voucher.view;

import com.prgmrs.voucher.controller.BlacklistController;
import com.prgmrs.voucher.controller.VoucherController;
import com.prgmrs.voucher.model.Voucher;
import com.prgmrs.voucher.setting.BlacklistProperties;
import com.prgmrs.voucher.model.VoucherValidator;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
public class ConsoleView implements CommandLineRunner {
    private final VoucherController voucherController;
    private final BlacklistController blackListController;
    private final ConsoleViewIO consoleViewIO;
    private final VoucherValidator voucherValidator;
    private final BlacklistProperties blacklistProperties;

    public ConsoleView(VoucherController voucherController, BlacklistController blackListController, ConsoleViewIO consoleViewIO, VoucherValidator voucherValidator, BlacklistProperties blacklistProperties) {
        this.voucherController = voucherController;
        this.blackListController = blackListController;
        this.consoleViewIO = consoleViewIO;
        this.voucherValidator = voucherValidator;
        this.blacklistProperties = blacklistProperties;
    }

    @Override
    public void run(String... args) {
        boolean continueRunning = true;
        while (continueRunning) {
            consoleViewIO.showCommand();
            ConsoleViewOptionEnum consoleViewOptionEnum =
                    ConsoleViewOptionEnum.findByCommand(consoleViewIO.read());
            continueRunning = selectMenu(consoleViewOptionEnum);
        }
    }

    private boolean selectMenu(ConsoleViewOptionEnum consoleViewOptionEnum) {
        boolean continueRunning = true;
        switch (consoleViewOptionEnum) {
            case EXIT_THE_LOOP -> continueRunning = false;
            case CREATE_THE_VOUCHER -> voucherSelectionPhase();
            case SHOW_THE_LIST -> {
                Map<UUID, Voucher> voucherHistory = voucherController.findAll();
                consoleViewIO.showList(voucherHistory);
            }
            case SHOW_BLACKLIST -> {
                if (!blacklistProperties.isBlacklistAllow())
                    break;
                Map<UUID, String> blacklist = blackListController.findAll();
                consoleViewIO.showBlacklist(blacklist);
            }
            case UNEXPECTED_INPUT -> consoleViewIO.write("Incorrect command typed.");
        }
        return continueRunning;
    }

    private void voucherSelectionPhase() {
        boolean continueRunning = true;
        while (continueRunning) {
            consoleViewIO.showVoucherCreationMessage();
            ConsoleViewVoucherCreationEnum consoleViewVoucherCreationEnum =
                    ConsoleViewVoucherCreationEnum.findByCommand(consoleViewIO.read());
            continueRunning = selectVoucher(consoleViewVoucherCreationEnum);
        }
    }

    private boolean selectVoucher(ConsoleViewVoucherCreationEnum consoleViewVoucherCreationEnum) {
        boolean continueRunning = true;
        switch (consoleViewVoucherCreationEnum) {
            case CREATE_FIXED_AMOUNT_VOUCHER -> {
                boolean isOperationSuccessful = voucherCreationPhase(ConsoleViewVoucherCreationEnum.CREATE_FIXED_AMOUNT_VOUCHER);
                if (isOperationSuccessful) continueRunning = false;
            }
            case CREATE_PERCENT_DISCOUNT_VOUCHER -> {
                boolean isOperationSuccessful = voucherCreationPhase(ConsoleViewVoucherCreationEnum.CREATE_PERCENT_DISCOUNT_VOUCHER);
                if (isOperationSuccessful) continueRunning = false;
            }
            case UNEXPECTED_INPUT -> consoleViewIO.write("incorrect command typed.");
        }
        return continueRunning;
    }

    private boolean voucherCreationPhase(ConsoleViewVoucherCreationEnum consoleViewVoucherCreationEnum) {
        boolean isOperationSuccessful = false;
        consoleViewIO.showSpecificCreationMessage(consoleViewVoucherCreationEnum);
        String token = consoleViewIO.read();
        long value = voucherValidator.stringToLongConverter(token);
        if (voucherValidator.isAmountValid(consoleViewVoucherCreationEnum, value)) {
            UUID uuid = voucherController.createVoucher(value, consoleViewVoucherCreationEnum);
            Voucher voucher = voucherController.findVoucherById(uuid);
            consoleViewIO.showVoucherResult(voucher);
            isOperationSuccessful = true;
        }
        return isOperationSuccessful;
    }
}
