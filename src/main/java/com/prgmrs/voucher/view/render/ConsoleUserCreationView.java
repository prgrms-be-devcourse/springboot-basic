package com.prgmrs.voucher.view.render;

import com.prgmrs.voucher.controller.UserController;
import com.prgmrs.voucher.dto.request.UserRequest;
import com.prgmrs.voucher.dto.response.UserResponse;
import com.prgmrs.voucher.exception.WrongRangeFormatException;
import com.prgmrs.voucher.view.ConsoleReader;
import com.prgmrs.voucher.view.writer.ConsoleCreationWriter;
import org.springframework.stereotype.Component;

@Component
public class ConsoleUserCreationView {
    private final ConsoleReader consoleReader;
    private final ConsoleCreationWriter consoleCreationWriter;
    private final UserController userController;

    public ConsoleUserCreationView(ConsoleReader consoleReader, ConsoleCreationWriter consoleCreationWriter, UserController userController) {
        this.consoleReader = consoleReader;
        this.consoleCreationWriter = consoleCreationWriter;
        this.userController = userController;
    }

    void createUser() {
        boolean continueRunning = true;
        while (continueRunning) {
            consoleCreationWriter.showNamingUser();
            String name = consoleReader.read();
            UserRequest userRequest = new UserRequest(name);
            try {
                UserResponse userResponse = userController.createUser(userRequest);
                consoleCreationWriter.showUserResult(userResponse);
                continueRunning = false;
            } catch (WrongRangeFormatException e) {
                consoleCreationWriter.write("incorrect name format");
            }
        }
    }
}
