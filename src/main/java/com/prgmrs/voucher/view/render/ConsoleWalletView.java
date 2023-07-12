package com.prgmrs.voucher.view.render;

import com.prgmrs.voucher.controller.UserController;
import com.prgmrs.voucher.controller.VoucherController;
import com.prgmrs.voucher.controller.WalletController;
import com.prgmrs.voucher.dto.request.WalletRequest;
import com.prgmrs.voucher.dto.response.UserListResponse;
import com.prgmrs.voucher.dto.response.VoucherListResponse;
import com.prgmrs.voucher.dto.response.WalletResponse;
import com.prgmrs.voucher.enums.WalletSelectionType;
import com.prgmrs.voucher.exception.NoSuchChoiceException;
import com.prgmrs.voucher.exception.WrongRangeFormatException;
import com.prgmrs.voucher.view.ConsoleReader;
import com.prgmrs.voucher.view.writer.ConsoleListWriter;
import com.prgmrs.voucher.view.writer.ConsoleWalletWriter;
import org.springframework.stereotype.Component;

@Component
public class ConsoleWalletView {
    private final ConsoleWalletWriter consoleWalletWriter;
    private final ConsoleReader consoleReader;
    private final ConsoleListWriter consoleListWriter;
    private final UserController userController;
    private final VoucherController voucherController;
    private final WalletController walletController;

    public ConsoleWalletView(ConsoleWalletWriter consoleWalletWriter, ConsoleReader consoleReader, ConsoleListWriter consoleListWriter, UserController userController, VoucherController voucherController, WalletController walletController) {
        this.consoleWalletWriter = consoleWalletWriter;
        this.consoleReader = consoleReader;
        this.consoleListWriter = consoleListWriter;
        this.userController = userController;
        this.voucherController = voucherController;
        this.walletController = walletController;
    }

    void selectWalletType() {
        boolean continueRunning = true;
        while (continueRunning) {
            consoleWalletWriter.showWalletType();
            try {
                WalletSelectionType walletSelectionType = WalletSelectionType.of(consoleReader.read());
                redirectWallet(walletSelectionType);
                continueRunning = false;
            } catch (NoSuchChoiceException e) {
                consoleWalletWriter.write("such wallet type not exist");
            }
        }
    }

    private void redirectWallet(WalletSelectionType walletSelectionType) {
        switch (walletSelectionType) {
            case ASSIGN_VOUCHER -> selectUser();
            case FREE_VOUCHER -> selectUserWithVoucher();
            case BACK -> {}
        }
    }

    private void selectUser() {
        boolean continueRunning = true;
        while (continueRunning) {
            UserListResponse userListResponse = userController.getAllUsers();
            consoleListWriter.showUserList(userListResponse);
            consoleWalletWriter.showNameUser(WalletSelectionType.ASSIGN_VOUCHER);
            String username = consoleReader.read();

            VoucherListResponse voucherListResponse = voucherController.getNotAssignedVoucherList();
            consoleListWriter.showVoucherList(voucherListResponse);

            consoleWalletWriter.showNumberVoucher(WalletSelectionType.ASSIGN_VOUCHER);
            String order = consoleReader.read();

            WalletRequest walletRequest = new WalletRequest(username, order, voucherListResponse.voucherList());
            try {
                WalletResponse walletResponse = walletController.assignVoucher(walletRequest);
                consoleWalletWriter.showWalletResult(walletResponse, WalletSelectionType.ASSIGN_VOUCHER);
                continueRunning = false;
            } catch (WrongRangeFormatException e) {
                consoleWalletWriter.write("incorrect format or value out of range");
            }
        }
    }

    private void selectUserWithVoucher() {
        boolean continueRunning = true;
        while (continueRunning) {
            UserListResponse userListResponse = userController.getUserListWithVoucherAssigned();
            consoleListWriter.showUserList(userListResponse);
            consoleWalletWriter.showNameUser(WalletSelectionType.FREE_VOUCHER);
            String username = consoleReader.read();

            VoucherListResponse voucherListResponse = voucherController.getAssignedVoucherListByUsername(username);
            consoleListWriter.showVoucherList(voucherListResponse);

            consoleWalletWriter.showNumberVoucher(WalletSelectionType.FREE_VOUCHER);
            String order = consoleReader.read();

            WalletRequest walletRequest = new WalletRequest(username, order, voucherListResponse.voucherList());
            try {
                WalletResponse walletResponse = walletController.freeVoucher(walletRequest);
                consoleWalletWriter.showWalletResult(walletResponse, WalletSelectionType.FREE_VOUCHER);
                continueRunning = false;
            } catch (WrongRangeFormatException e) {
                consoleWalletWriter.write("incorrect format or value out of range");
            }
        }
    }
}
