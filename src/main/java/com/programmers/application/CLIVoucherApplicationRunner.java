package com.programmers.application;

import com.programmers.application.controller.FrontController;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("cli")
public class CLIVoucherApplicationRunner implements ApplicationRunner {
    private final FrontController frontController;

    public CLIVoucherApplicationRunner(FrontController frontController) {
        this.frontController = frontController;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        while (true) {
            frontController.process();
        }
    }
}
