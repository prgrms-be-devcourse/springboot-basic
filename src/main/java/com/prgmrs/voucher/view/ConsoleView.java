package com.prgmrs.voucher.view;

import com.prgmrs.voucher.controller.BlacklistController;
import com.prgmrs.voucher.controller.VoucherController;
import com.prgmrs.voucher.dto.BlacklistResponse;
import com.prgmrs.voucher.dto.VoucherListResponse;
import com.prgmrs.voucher.dto.VoucherRequest;
import com.prgmrs.voucher.enums.ConsoleViewOption;
import com.prgmrs.voucher.enums.VoucherType;
import com.prgmrs.voucher.exception.NoSuchOptionException;
import com.prgmrs.voucher.exception.NoSuchVoucherTypeException;
import com.prgmrs.voucher.model.Voucher;
import com.prgmrs.voucher.model.VoucherValidator;
import com.prgmrs.voucher.model.vo.DiscountValue;
import com.prgmrs.voucher.setting.BlacklistProperties;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;
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
            try {
                ConsoleViewOption consoleViewOption = ConsoleViewOption.of(consoleViewIO.read());
                continueRunning = selectMenu(consoleViewOption);
            } catch (NoSuchOptionException e) {
                consoleViewIO.write("no such option exist");
            }
        }
    }

    private boolean selectMenu(ConsoleViewOption consoleViewOption) {
        boolean continueRunning = true;
        switch (consoleViewOption) {
            case EXIT_THE_LOOP -> continueRunning = false;
            case CREATE_THE_VOUCHER -> voucherSelectionPhase();
            case SHOW_THE_LIST -> {
                VoucherListResponse voucherListResponse = voucherController.findAll();
                consoleViewIO.showList(voucherListResponse);
            }
            case SHOW_BLACKLIST -> {
                if (!blacklistProperties.isBlacklistAllow())
                    break;
                BlacklistResponse blacklist = blackListController.findAll();
                consoleViewIO.showBlacklist(blacklist);
            }
        }
        return continueRunning;
    }

    private void voucherSelectionPhase() {
        boolean continueRunning = true;
        while (continueRunning) {
            consoleViewIO.showVoucherCreationMessage();
            try {
                VoucherType voucherType = VoucherType.of(consoleViewIO.read());
                selectVoucher(voucherType);
                continueRunning = false;
            } catch (NoSuchVoucherTypeException e) {
                consoleViewIO.write("no such voucher type exist");
            }
        }
    }

    private void selectVoucher(VoucherType voucherType) {
        switch (voucherType) {
            case FIXED_AMOUNT_VOUCHER -> voucherCreationPhase(VoucherType.FIXED_AMOUNT_VOUCHER);
            case PERCENT_DISCOUNT_VOUCHER -> voucherCreationPhase(VoucherType.PERCENT_DISCOUNT_VOUCHER);
        }
    }

    private void voucherCreationPhase(VoucherType voucherType) {
        boolean continueRunning = true;
        while (continueRunning) {
            consoleViewIO.showSpecificCreationMessage(voucherType);
            String token = consoleViewIO.read();
            Optional<Long> convertedValue = voucherValidator.stringToLongConverter(token);

            if (convertedValue.isEmpty()) {
                continue;
            }

            DiscountValue discountValue = new DiscountValue(convertedValue.get());

            if (voucherValidator.isAmountValid(voucherType, discountValue)) {
                VoucherRequest voucherRequest = new VoucherRequest(voucherType, discountValue);
                UUID uuid = voucherController.createVoucher(voucherRequest);
                Voucher voucher = voucherController.findVoucherById(uuid);
                consoleViewIO.showVoucherResult(voucher);
                continueRunning = false;
            }
        }
    }
}
