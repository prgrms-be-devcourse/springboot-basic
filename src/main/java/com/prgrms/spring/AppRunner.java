package com.prgrms.spring;

import com.prgrms.spring.controller.voucher.VoucherController;
import com.prgrms.spring.domain.menu.MenuType;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.util.Scanner;

@AllArgsConstructor
@Component
public class AppRunner implements CommandLineRunner {
    private final VoucherController voucherController;
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void run(String... args) throws Exception {
        boolean isExecute = true;
        while (isExecute) {
            for (MenuType mt : MenuType.values()) {
                System.out.println(mt.getName() + " -> " + mt.getExplain());
            }
            String type = scanner.nextLine();
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
