package com.prgmrs.voucher.view;

import com.prgmrs.voucher.controller.BlacklistController;
import com.prgmrs.voucher.controller.VoucherController;
import com.prgmrs.voucher.exception.WrongRangeFormatException;
import com.prgmrs.voucher.model.Voucher;
import com.prgmrs.voucher.setting.BlacklistProperties;
import com.prgmrs.voucher.setting.VoucherProperties;
import com.prgmrs.voucher.util.InputHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
public class ConsoleView {
    private final VoucherController voucherController;
    private final BlacklistController blackListController;
    private final ConsoleViewIO consoleViewIO = new ConsoleViewIO();
    private final InputHandler inputHandler = new InputHandler();
    private final BlacklistProperties blacklistProperties;
    private final VoucherProperties voucherProperties;

    @Autowired
    public ConsoleView(VoucherController voucherController, BlacklistController blackListController,
                       BlacklistProperties blacklistProperties, VoucherProperties voucherProperties) {
        this.voucherController = voucherController;
        this.blackListController = blackListController;
        this.blacklistProperties = blacklistProperties;
        this.voucherProperties = voucherProperties;
    }

    public void run() {
        boolean continueRunning = true;
        while (continueRunning) {
            consoleViewIO.showCommand(blacklistProperties.isBlacklistAllow());
            ConsoleViewOptionEnum consoleViewOptionEnum =
                    ConsoleViewOptionEnum.findByCommand(consoleViewIO.read());
            switch (consoleViewOptionEnum) {
                case EXIT_THE_LOOP -> continueRunning = false;
                case CREATE_THE_VOUCHER -> selectVoucher();
                case SHOW_THE_LIST -> {
                    Map<UUID, Voucher> voucherHistory = voucherController.findAll();
                    consoleViewIO.showList(voucherHistory);
                }
                case SHOW_BLACKLIST -> {
                    if (!blacklistProperties.isBlacklistAllow())
                        break;
                    Map<UUID, String> blacklist = blackListController.findAll();
                    consoleViewIO.showBlacklist(blacklist, blacklistProperties.isBlacklistShowId());
                }
                case UNEXPECTED_INPUT -> consoleViewIO.write("Incorrect command typed.");
            }
        }
    }

    private void selectVoucher() {
        boolean continueRunning = true;
        while (continueRunning) {
            consoleViewIO.showVoucherCreationMessage();
            ConsoleViewVoucherCreationEnum consoleViewVoucherCreationEnum =
                    ConsoleViewVoucherCreationEnum.findByCommand(consoleViewIO.read());
            switch (consoleViewVoucherCreationEnum) {
                case CREATE_FIXED_AMOUNT_VOUCHER -> {
                    try {
                        voucherCreation(ConsoleViewVoucherCreationEnum.CREATE_FIXED_AMOUNT_VOUCHER);
                    } catch(WrongRangeFormatException we) {
                        consoleViewIO.write("incorrect format or out of range value.");
                        break;
                    }
                    continueRunning = false;
                }
                case CREATE_PERCENT_DISCOUNT_VOUCHER -> {
                    try {
                        voucherCreation(ConsoleViewVoucherCreationEnum.CREATE_PERCENT_DISCOUNT_VOUCHER);
                    } catch(WrongRangeFormatException we) {
                        consoleViewIO.write("incorrect format or out of range value.");
                        break;
                    }
                    continueRunning = false;
                }
                case UNEXPECTED_INPUT -> consoleViewIO.write("incorrect command typed.");
            }
        }
    }

    private void voucherCreation(ConsoleViewVoucherCreationEnum consoleViewVoucherCreationEnum) throws WrongRangeFormatException {
            consoleViewIO.showSpecificCreationMessage(consoleViewVoucherCreationEnum, voucherProperties.getMaximumFixedAmount());
            String token = consoleViewIO.read();
            long value = inputHandler.stringToLongConverter(token);
            inputHandler.isAmountValid(consoleViewVoucherCreationEnum, voucherProperties.getMaximumFixedAmount(), value);
            UUID uuid = voucherController.createVoucher(value, consoleViewVoucherCreationEnum);
            Voucher voucher = voucherController.findVoucherById(uuid);
            consoleViewIO.showVoucherResult(voucher);
    }
}
