package org.prgrms.deukyun.voucherapp.app;

import org.prgrms.deukyun.voucherapp.app.menu.Menu;
import org.prgrms.deukyun.voucherapp.order.service.OrderService;
import org.prgrms.deukyun.voucherapp.voucher.service.VoucherService;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * 바우처 애플리케이션 러너 - 바우처 애플리케이션의 로직의 실행을 책임짐
 */
@Component
public class VoucherAppRunner {
    private final OrderService orderService;
    private final VoucherService voucherService;
    private final List<Menu> menus;
    private boolean exit;

    public VoucherAppRunner(OrderService orderService, VoucherService voucherService, List<Menu> menus) {
        this.orderService = orderService;
        this.voucherService = voucherService;
        this.menus = menus;
        exit = false;
    }

    public void run() {
        showMenu();
        /*
        TODO
           - 메뉴 입력 받기
           - 입력에 따른 로직 구현
       */
        exit = true;
    }

    private void showMenu() {
        System.out.println("=== Voucher Program ===");
        menus.forEach(Menu::display);
        System.out.println();
    }

    public boolean isExit() {
        return exit;
    }
}
