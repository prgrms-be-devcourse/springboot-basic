package com.programmers.application;

import com.programmers.application.controller.Controller;
import com.programmers.application.controller.FrontController;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class CLIVoucherApplicationRunner implements ApplicationRunner {
    private final ApplicationContext applicationContext;

    public CLIVoucherApplicationRunner(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        applicationContext.getBean(Controller.class);
        Controller controller = applicationContext.getBean(FrontController.class);
        while (true) {
            controller.process();
        }
    }
}
