package com.prgrms.spring;

import com.prgrms.spring.controller.voucher.VoucherController;
import com.prgrms.spring.domain.menu.MenuType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;

import java.io.Console;

@AllArgsConstructor
@Controller
public class AppRunner implements Runnable{
    private final VoucherController voucherController;
    private static final Console console = System.console();

    @Override
    public void run() {
        boolean isExecute = true;
        while (isExecute) {
            String type = console.readLine("menu: ");
            MenuType menuType = MenuType.matchType(type);
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
                    System.out.println("Error");
                    break;
            }
        }
    }
}
