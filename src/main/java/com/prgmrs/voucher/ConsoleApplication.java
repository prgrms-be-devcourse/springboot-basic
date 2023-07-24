package com.prgmrs.voucher;

import com.prgmrs.voucher.controller.console.BlacklistController;
import com.prgmrs.voucher.controller.console.UserController;
import com.prgmrs.voucher.controller.console.VoucherController;
import com.prgmrs.voucher.controller.console.WalletController;
import com.prgmrs.voucher.controller.console.wrapper.ResponseDTO;
import com.prgmrs.voucher.exception.WrongRangeFormatException;
import com.prgmrs.voucher.setting.BlacklistProperties;
import com.prgmrs.voucher.view.RequestBodyCreator;
import com.prgmrs.voucher.view.io.ConsoleReader;
import com.prgmrs.voucher.view.io.ConsoleWriter;
import com.prgmrs.voucher.view.io.ManagementType;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ConsoleApplication implements CommandLineRunner {
    private final BlacklistProperties blacklistProperties;

    private final ConsoleReader consoleReader;
    private final ConsoleWriter consoleWriter;
    private final RequestBodyCreator requestBodyCreator;

    private final VoucherController voucherController;
    private final UserController userController;
    private final WalletController walletController;
    private final BlacklistController blacklistController;

    public ConsoleApplication(BlacklistProperties blacklistProperties, ConsoleReader consoleReader, RequestBodyCreator requestBodyCreator, VoucherController voucherController, UserController userController, WalletController walletController, ConsoleWriter consoleWriter, BlacklistController blacklistController) {
        this.blacklistProperties = blacklistProperties;
        this.consoleReader = consoleReader;
        this.requestBodyCreator = requestBodyCreator;
        this.voucherController = voucherController;
        this.userController = userController;
        this.walletController = walletController;
        this.consoleWriter = consoleWriter;
        this.blacklistController = blacklistController;
    }

    @Override
    public void run(String... args) {
        while (true) {
            boolean continueRunning;
            consoleWriter.showManagementType();
            try {
                ManagementType managementType = ManagementType.of(consoleReader.read());
                continueRunning = redirectManagementType(managementType);
                if (!continueRunning) {
                    break;
                }
            } catch (WrongRangeFormatException e) {
                consoleWriter.write(e.getMessage());
            }
        }
    }

    private boolean redirectManagementType(ManagementType managementType) {
        switch (managementType) {
            case EXIT_THE_LOOP -> {
                return false;
            }
            case CREATE_USER -> {
                ResponseDTO<?> userResponse = userController.createUser(requestBodyCreator.createUserRequest());
                consoleWriter.showResultMessage(userResponse);
            }
            case FIXED_AMOUNT_VOUCHER, PERCENT_DISCOUNT_VOUCHER -> {
                ResponseDTO<?> voucherResponse =
                        voucherController.createVoucher(requestBodyCreator.createVoucherRequest(managementType));
                consoleWriter.showResultMessage(voucherResponse);
            }
            case ASSIGN_VOUCHER -> {
                ResponseDTO<?> walletResponse =
                        walletController.assignVoucher(requestBodyCreator.createAssignVoucherRequest());
                consoleWriter.showResultMessage(walletResponse);
            }
            case REMOVE_VOUCHER -> {
                ResponseDTO<?> walletResponse =
                        walletController.removeVoucher(requestBodyCreator.createRemoveVoucherRequest());
                consoleWriter.showResultMessage(walletResponse);
            }
            case SHOW_ALL_VOUCHERS -> {
                ResponseDTO<?> voucherListResponse = voucherController.findAll();
                consoleWriter.showResultMessage(voucherListResponse);
            }
            case SHOW_USER_WALLET -> {
                ResponseDTO<?> voucherListResponse =
                        voucherController.getAssignedVoucherListByUsername(requestBodyCreator.createUsernameRequest());
                consoleWriter.showResultMessage(voucherListResponse);
            }
            case SHOW_VOUCHER_OWNER -> {
                ResponseDTO<?> userResponse =
                        userController.getUserByVoucherId(requestBodyCreator.createVoucherIdRequest());
                consoleWriter.showResultMessage(userResponse);
            }
            case SHOW_BLACKLIST -> {
                if (!blacklistProperties.isBlacklistAllow()) {
                    consoleWriter.write("got no access to the command");
                    break;
                }
                ResponseDTO<?> blacklistResponse = blacklistController.findAll();
                consoleWriter.showResultMessage(blacklistResponse);
            }
        }
        return true;
    }
}
