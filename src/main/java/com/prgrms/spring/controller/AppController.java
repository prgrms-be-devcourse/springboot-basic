package com.prgrms.spring.controller;

import com.prgrms.spring.controller.voucher.VoucherController;
import com.prgrms.spring.domain.menu.MenuType;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AppController {
    private final VoucherController voucherController;

    public void run() {
        boolean isExecute = true;
        while (isExecute) {
            String type = "Sf";
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

                    break;
            }
        }
    }
}
