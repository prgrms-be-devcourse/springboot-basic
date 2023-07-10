package com.prgmrs.voucher.view;

import com.prgmrs.voucher.controller.BlacklistController;
import com.prgmrs.voucher.controller.VoucherController;
import com.prgmrs.voucher.dto.BlacklistResponse;
import com.prgmrs.voucher.dto.VoucherListResponse;
import com.prgmrs.voucher.dto.VoucherRequest;
import com.prgmrs.voucher.dto.VoucherResponse;
import com.prgmrs.voucher.enums.ConsoleViewOption;
import com.prgmrs.voucher.enums.VoucherType;
import com.prgmrs.voucher.exception.NoSuchOptionException;
import com.prgmrs.voucher.exception.NoSuchVoucherTypeException;
import com.prgmrs.voucher.exception.WrongRangeFormatException;
import com.prgmrs.voucher.setting.BlacklistProperties;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ConsoleView implements CommandLineRunner {
    private final VoucherController voucherController;
    private final BlacklistController blackListController;
    private final ConsoleReader consoleReader;
    private final ConsoleWriter consoleWriter;
    private final BlacklistProperties blacklistProperties;

    public ConsoleView(VoucherController voucherController, BlacklistController blackListController, ConsoleReader consoleReader, ConsoleWriter consoleWriter, BlacklistProperties blacklistProperties) {
        this.voucherController = voucherController;
        this.blackListController = blackListController;
        this.consoleReader = consoleReader;
        this.consoleWriter = consoleWriter;
        this.blacklistProperties = blacklistProperties;
    }

    @Override
    public void run(String... args) {
        boolean continueRunning = true;
        while (continueRunning) {
            consoleWriter.showCommand();
            try {
                ConsoleViewOption consoleViewOption = ConsoleViewOption.of(consoleReader.read());
                continueRunning = selectOption(consoleViewOption);
            } catch (NoSuchOptionException e) {
                consoleWriter.write("such option not exist");
            }
        }
    }

    private boolean selectOption(ConsoleViewOption consoleViewOption) {
        boolean continueRunning = true;
        switch (consoleViewOption) {
            case EXIT_THE_LOOP -> continueRunning = false;
            case CREATE_THE_VOUCHER -> selectVoucher();
            case SHOW_THE_LIST -> {
                VoucherListResponse voucherListResponse = voucherController.findAll();
                consoleWriter.showList(voucherListResponse);
            }
            case SHOW_BLACKLIST -> {
                if (!blacklistProperties.isBlacklistAllow())
                    break;
                BlacklistResponse blacklist = blackListController.findAll();
                consoleWriter.showBlacklist(blacklist);
            }
        }
        return continueRunning;
    }

    private void selectVoucher() {
        boolean continueRunning = true;
        while (continueRunning) {
            consoleWriter.showVoucherCreationMessage();
            try {
                VoucherType voucherType = VoucherType.of(consoleReader.read());
                createVoucher(voucherType);
                continueRunning = false;
            } catch (NoSuchVoucherTypeException e) {
                consoleWriter.write("such voucher type not exist");
            }
        }
    }

    private void createVoucher(VoucherType voucherType) {
        boolean continueRunning = true;
        while (continueRunning) {
            consoleWriter.showSpecificCreationMessage(voucherType);
            String token = consoleReader.read();
            VoucherRequest voucherRequest = new VoucherRequest(voucherType, token);
            try {
                VoucherResponse voucherResponse = voucherController.createVoucher(voucherRequest);
                consoleWriter.showVoucherResult(voucherResponse);
                continueRunning = false;
            } catch (WrongRangeFormatException e) {
                consoleWriter.write("incorrect format or value out of range");
            }
        }
    }
}
