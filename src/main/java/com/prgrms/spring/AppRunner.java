package com.prgrms.spring;

import com.prgrms.spring.controller.voucher.VoucherController;
import com.prgrms.spring.domain.menu.MenuType;
import com.prgrms.spring.exception.Error;
import com.prgrms.spring.io.ConsoleView;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@AllArgsConstructor
@Component
public class AppRunner implements CommandLineRunner {
    private final VoucherController voucherController;

    private final ConsoleView consoleView;

    @Override
    public void run(String... args) throws Exception {
        boolean isExecute = true;
        while (isExecute) {
            consoleView.showMenu();
            MenuType menuType = MenuType.matchType(consoleView.getMenu());
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
                default:
                    consoleView.showErrorMsg(Error.VALIDATION_WRONG_TYPE);
                    break;
            }
        }
    }
}
