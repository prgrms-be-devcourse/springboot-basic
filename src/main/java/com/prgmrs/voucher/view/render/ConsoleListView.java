package com.prgmrs.voucher.view.render;

import com.prgmrs.voucher.controller.BlacklistController;
import com.prgmrs.voucher.controller.UserController;
import com.prgmrs.voucher.controller.VoucherController;
import com.prgmrs.voucher.dto.request.UserListRequest;
import com.prgmrs.voucher.dto.response.BlacklistResponse;
import com.prgmrs.voucher.dto.response.UserListResponse;
import com.prgmrs.voucher.dto.response.UserResponse;
import com.prgmrs.voucher.dto.response.VoucherListResponse;
import com.prgmrs.voucher.enums.ListSelectionType;
import com.prgmrs.voucher.exception.NoSuchChoiceException;
import com.prgmrs.voucher.exception.WrongRangeFormatException;
import com.prgmrs.voucher.setting.BlacklistProperties;
import com.prgmrs.voucher.view.ConsoleReader;
import com.prgmrs.voucher.view.writer.ConsoleCreationWriter;
import com.prgmrs.voucher.view.writer.ConsoleListWriter;
import org.springframework.stereotype.Component;

@Component
public class ConsoleListView {
    private final VoucherController voucherController;
    private final ConsoleListWriter consoleListWriter;
    private final BlacklistController blacklistController;
    private final BlacklistProperties blacklistProperties;
    private final ConsoleReader consoleReader;
    private final ConsoleCreationWriter consoleCreationWriter;
    private final UserController userController;

    public ConsoleListView(VoucherController voucherController, ConsoleListWriter consoleListWriter, BlacklistController blacklistController, BlacklistProperties blacklistProperties, ConsoleReader consoleReader, ConsoleCreationWriter consoleCreationWriter, UserController userController) {
        this.voucherController = voucherController;
        this.consoleListWriter = consoleListWriter;
        this.blacklistController = blacklistController;
        this.blacklistProperties = blacklistProperties;
        this.consoleReader = consoleReader;
        this.consoleCreationWriter = consoleCreationWriter;
        this.userController = userController;
    }

    void selectListType() {
        boolean continueRunning = true;
        while (continueRunning) {
            consoleListWriter.showListType();
            try {
                ListSelectionType listSelectionType = ListSelectionType.of(consoleReader.read());
                redirectListType(listSelectionType);
                continueRunning = false;
            } catch (NoSuchChoiceException e) {
                consoleCreationWriter.write("such list type not exist");
            }
        }
    }

    private void redirectListType(ListSelectionType listSelectionType) {
        switch (listSelectionType) {
            case SHOW_ALL_VOUCHERS -> {
                VoucherListResponse voucherListResponse = voucherController.findAll();
                consoleListWriter.showVoucherList(voucherListResponse);
            }
            case SHOW_USER_WALLET -> selectUser();
            case SHOW_VOUCHER_OWNER -> selectVoucher();
            case SHOW_BLACKLIST -> {
                if (!blacklistProperties.isBlacklistAllow())
                    break;
                BlacklistResponse blacklist = blacklistController.findAll();
                consoleListWriter.showBlacklist(blacklist);
            }
            case BACK -> {}
        }
    }

    private void selectUser() {
        boolean continueRunning = true;
        while (continueRunning) {
            UserListResponse userListResponse = userController.getUserListWithVoucherAssigned();
            consoleListWriter.showUserList(userListResponse);

            consoleListWriter.showWhichUser();
            String username = consoleReader.read();

            try {
                VoucherListResponse voucherListResponse = voucherController.getAssignedVoucherListByUsername(username);
                consoleListWriter.showVoucherList(voucherListResponse);
                continueRunning = false;
            } catch (WrongRangeFormatException e) {
                consoleListWriter.write("incorrect format or value out of range");
            }
        }
    }

    private void selectVoucher() {
        boolean continueRunning = true;
        while (continueRunning) {
            VoucherListResponse voucherListResponse = voucherController.getAssignedVoucherList();
            consoleListWriter.showVoucherList(voucherListResponse);

            consoleListWriter.showWhichVoucher();
            String order = consoleReader.read();

            UserListRequest userListRequest = new UserListRequest(order, voucherListResponse.voucherList());

            try {
                UserResponse userResponse = userController.getUserByVoucherId(userListRequest);
                consoleListWriter.showUser(userResponse);
                continueRunning = false;
            } catch (WrongRangeFormatException e) {
                consoleListWriter.write("incorrect format or value out of range");
            }
        }
    }
}
