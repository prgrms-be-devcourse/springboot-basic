package com.prgmrs.voucher.view.render;

import com.prgmrs.voucher.controller.BlacklistController;
import com.prgmrs.voucher.controller.UserController;
import com.prgmrs.voucher.controller.VoucherController;
import com.prgmrs.voucher.dto.UserRequest;
import com.prgmrs.voucher.dto.UserResponse;
import com.prgmrs.voucher.exception.WrongRangeFormatException;
import com.prgmrs.voucher.setting.BlacklistProperties;
import com.prgmrs.voucher.view.ConsoleReader;
import com.prgmrs.voucher.view.writer.ConsoleCreationWriter;
import org.springframework.stereotype.Component;

@Component
public class ConsoleUserCreationView {
    private final VoucherController voucherController;
    private final BlacklistController blackListController;
    private final ConsoleReader consoleReader;
    private final ConsoleCreationWriter consoleCreationWriter;
    private final BlacklistProperties blacklistProperties;
    private final UserController userController;

    public ConsoleUserCreationView(VoucherController voucherController, BlacklistController blackListController, ConsoleReader consoleReader, ConsoleCreationWriter consoleCreationWriter, BlacklistProperties blacklistProperties, UserController userController) {
        this.voucherController = voucherController;
        this.blackListController = blackListController;
        this.consoleReader = consoleReader;
        this.consoleCreationWriter = consoleCreationWriter;
        this.blacklistProperties = blacklistProperties;
        this.userController = userController;
    }

    void createUser() {
        boolean continueRunning = true;
        while (continueRunning) {
            consoleCreationWriter.showNamingUser();
            String name = consoleReader.read();
            UserRequest userRequest = new UserRequest(name);
            try {
                UserResponse UserResponse = userController.createUser(userRequest);
                consoleCreationWriter.showUserResult(UserResponse);
                continueRunning = false;
            } catch (WrongRangeFormatException e) {
                consoleCreationWriter.write("incorrect name format");
            }
        }
    }
}
