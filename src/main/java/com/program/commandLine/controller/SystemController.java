package com.program.commandLine.controller;

import com.program.commandLine.CommandLineProgramApplication;
import com.program.commandLine.io.Console;
import com.program.commandLine.io.MenuType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component(value = "systemController")
public class SystemController implements Runnable {

    private final Console console;
    private final VoucherController voucherController;
    private final CustomerController customerController;

    final Logger logger = LoggerFactory.getLogger(CommandLineProgramApplication.class);

    public SystemController(Console console, VoucherController voucherController, CustomerController customerController) {
        this.console = console;
        this.voucherController = voucherController;
        this.customerController = customerController;
    }


    @Override
    public void run() {
        boolean systemFlag = true;
        while (systemFlag) {
            console.menuView(MenuType.MAIN);
            String choseMenu = console.input();
            try {
                MenuType menuType = MenuType.valueOf(choseMenu.toUpperCase());
                switch (menuType) {
                    case CUSTOMER -> customerController.run();
                    case VOUCHER -> voucherController.run();
                    case EXIT -> {
                        console.successMessageView("* 프로그램 종료 * ");
                        systemFlag = false;
                    }
                }
            } catch (Exception e) {
                logger.warn("컨트롤러 run 메소드에서 에러 처리 :" + e.getMessage());
                console.errorMessageView(e.getMessage());
            }

        }
    }

}
