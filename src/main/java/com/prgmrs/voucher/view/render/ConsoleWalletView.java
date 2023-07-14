package com.prgmrs.voucher.view.render;

import com.prgmrs.voucher.controller.UserController;
import com.prgmrs.voucher.controller.VoucherController;
import com.prgmrs.voucher.controller.WalletController;
import com.prgmrs.voucher.dto.ResponseDTO;
import com.prgmrs.voucher.dto.request.WalletRequest;
import com.prgmrs.voucher.dto.response.UserListResponse;
import com.prgmrs.voucher.dto.response.VoucherListResponse;
import com.prgmrs.voucher.dto.response.WalletResponse;
import com.prgmrs.voucher.enums.WalletAssignmentSelectionType;
import com.prgmrs.voucher.exception.NoSuchChoiceException;
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

    void selectWalletAssignmentType() {
        boolean continueRunning = true;
        while (continueRunning) {
            consoleWalletWriter.showVoucherAssignmentSelectionType();
            try {
                WalletAssignmentSelectionType walletAssignmentSelectionType = WalletAssignmentSelectionType.of(consoleReader.read());
                redirectWallet(walletAssignmentSelectionType);
                continueRunning = false;
            } catch (NoSuchChoiceException e) {
                consoleWalletWriter.write("such wallet type not exist");
            }
        }
    }

    private void redirectWallet(WalletAssignmentSelectionType walletAssignmentSelectionType) {
        switch (walletAssignmentSelectionType) {
            case ASSIGN_VOUCHER -> assignVoucher();
            case REMOVE_VOUCHER -> removeVoucher();
            case BACK -> {}
        }
    }

    private void assignVoucher() {
        boolean continueRunning = true;
        while (continueRunning) {
            UserListResponse userListResponse = userController.getAllUsers();
            consoleListWriter.showUserList(userListResponse);
            consoleWalletWriter.showNameUser(WalletAssignmentSelectionType.ASSIGN_VOUCHER);
            String username = consoleReader.read();

            VoucherListResponse voucherListResponse = voucherController.getNotAssignedVoucherList();
            consoleListWriter.showVoucherList(voucherListResponse);

            consoleWalletWriter.showNumberVoucher(WalletAssignmentSelectionType.ASSIGN_VOUCHER);
            String order = consoleReader.read();

            WalletRequest walletRequest = new WalletRequest(username, order, voucherListResponse.voucherList());

            ResponseDTO<WalletResponse> walletResponseDTO = walletController.assignVoucher(walletRequest);
            WalletResponse walletResponse = walletResponseDTO.getData();
            if(walletResponseDTO.isError()) {
                consoleWalletWriter.write("incorrect format or value out of range");
            } else {
                consoleWalletWriter.showWalletResult(walletResponse, WalletAssignmentSelectionType.ASSIGN_VOUCHER);
                continueRunning = false;
            }
        }
    }

    private void removeVoucher() {
        boolean continueRunning = true;
        while (continueRunning) {
            UserListResponse userListResponse = userController.getUserListWithVoucherAssigned();
            consoleListWriter.showUserList(userListResponse);
            consoleWalletWriter.showNameUser(WalletAssignmentSelectionType.REMOVE_VOUCHER);
            String username = consoleReader.read();

            VoucherListResponse voucherListResponse = voucherController.getAssignedVoucherListByUsername(username);
            consoleListWriter.showVoucherList(voucherListResponse);

            consoleWalletWriter.showNumberVoucher(WalletAssignmentSelectionType.REMOVE_VOUCHER);
            String order = consoleReader.read();

            WalletRequest walletRequest = new WalletRequest(username, order, voucherListResponse.voucherList());

            ResponseDTO<WalletResponse> walletResponseDTO = walletController.removeVoucher(walletRequest);
            WalletResponse walletResponse = walletResponseDTO.getData();
            if(walletResponseDTO.isError()) {
                consoleWalletWriter.write("incorrect format or value out of range");
            } else {
                consoleWalletWriter.showWalletResult(walletResponse, WalletAssignmentSelectionType.REMOVE_VOUCHER);
                continueRunning = false;
            }
        }
    }
}
