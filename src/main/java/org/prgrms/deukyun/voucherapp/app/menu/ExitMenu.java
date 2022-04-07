package org.prgrms.deukyun.voucherapp.app.menu;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 퇴장 메뉴
 */
@Component
@Order(1)
public class ExitMenu implements Menu {

    @Override
    public void display() {
        System.out.println("Type exit to exit the program.");
    }
}
