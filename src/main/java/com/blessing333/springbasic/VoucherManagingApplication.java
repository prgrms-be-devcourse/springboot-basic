package com.blessing333.springbasic;

import com.blessing333.springbasic.ui.UserInterface;
import com.blessing333.springbasic.voucher.CommandController;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VoucherManagingApplication implements ApplicationRunner {
    private final UserInterface userInterface;
    private final CommandController controller;

    @Override
    public void run(ApplicationArguments args){
        boolean shouldQuit;
        do{
            userInterface.showWelcomeText();
            String command = userInterface.inputMessage();
            shouldQuit = controller.parseCommand(command);
        }
        while (!shouldQuit);
    }
}
