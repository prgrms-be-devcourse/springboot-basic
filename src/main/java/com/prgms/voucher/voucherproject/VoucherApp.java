package com.prgms.voucher.voucherproject;

import com.prgms.voucher.voucherproject.domain.MenuType;
import com.prgms.voucher.voucherproject.domain.VoucherType;
import com.prgms.voucher.voucherproject.io.Console;
import com.prgms.voucher.voucherproject.io.Constant;
import com.prgms.voucher.voucherproject.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;


@Component
public class VoucherApp implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(VoucherApp.class);

    private final Console console = new Console();
    private final VoucherService voucherService;

    public VoucherApp(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @Override
    public void run(String... args) throws Exception {
        String menuName;
        boolean exit = false;
        MenuType inputCommand = null;
        VoucherType voucherType = null;

        while (!exit) {
            console.printMessage(Constant.CONSOLE_MENU, true);
            menuName = console.inputCommand().toLowerCase();

            try {
                inputCommand = MenuType.getSelectedMenuType(menuName);
            } catch (InputMismatchException e) {
                logger.error("MenuType InputMismatchException -> {}", menuName);
                console.printErrorMsg();
                continue;
            }

            switch (inputCommand) {
                case EXIT -> {
                    exit = true;
                    console.printMessage("프로그램을 종료합니다.", true);
                }
                case CREATE -> {
                    console.printMessage(Constant.CONSOLE_VOUCHER_MENU, false);
                    String selectedNum = console.inputCommand();

                    try {
                        voucherType = VoucherType.getSelectedVoucherType(selectedNum);
                    } catch (Exception e) {
                        logger.error("VoucherType InputMismatchException -> {}", selectedNum);
                        System.out.println(e.getLocalizedMessage());
                        continue;
                    }

                    voucherService.create(voucherType);
                }
                case LIST -> voucherService.list();
            }

        }
    }
}