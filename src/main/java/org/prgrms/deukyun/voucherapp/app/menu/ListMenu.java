package org.prgrms.deukyun.voucherapp.app.menu;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 목록 메뉴
 */
@Component
@Order(3)
public class ListMenu implements Menu {

    @Override
    public void display() {
        System.out.println("Type list to list all vouchers.");
    }
}
