package org.prgrms.deukyun.voucherapp.app.menu;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 생성 메뉴
 */
@Component
@Order(2)
public class CreateMenu implements Menu {

    @Override
    public void display() {
        System.out.println("Type create to create a new voucher.");
    }
}
