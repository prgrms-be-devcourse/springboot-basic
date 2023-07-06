package com.prgrms.spring;

import com.prgrms.spring.controller.CustomerController;
import com.prgrms.spring.controller.voucher.VoucherController;
import com.prgrms.spring.domain.menu.MenuType;
import com.prgrms.spring.exception.Error;
import com.prgrms.spring.io.ConsoleView;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@AllArgsConstructor
@Component
public class AppRunner implements CommandLineRunner {
    private final VoucherController voucherController;
    private final CustomerController customerController;

    private final ConsoleView consoleView;

    private static final Logger logger = LoggerFactory.getLogger(AppRunner.class);

    @Override
    public void run(String... args) throws Exception {
        boolean isExecute = true;
        while (isExecute) {
            logger.info("프로그램 시작.");
            consoleView.showMenu();
            MenuType menuType = null;
            try {
                menuType = MenuType.matchType(consoleView.getMenu());
            } catch (IllegalStateException e) {
                logger.error("input error!");
                consoleView.showErrorMsg(Error.VALIDATION_WRONG_TYPE);
                continue;
            }
            switch (menuType) {
                case EXIT:
                    isExecute = false;
                    break;
                case CREATE_VOUCHER:
                    voucherController.createVoucher();
                    break;
                case LIST_VOUCHER:
                    voucherController.getAllVoucher();
                    break;
                case CREATE_CUSTOMER:
                    customerController.createCustomer();
                    break;
                case LIST_CUSTOMER:
                    customerController.getAllCustomers();
                    break;
            }
        }
    }
}
