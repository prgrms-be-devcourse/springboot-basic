package org.prgrms.deukyun.voucherapp.app.menu;

import org.prgrms.deukyun.voucherapp.domain.voucher.service.VoucherService;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 목록 메뉴
 */
@Component
@Order(3)
public class ShowAllVoucherMenu implements Menu {

    private final VoucherService voucherService;

    public ShowAllVoucherMenu(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @Override
    public void display() {
        System.out.println("Type list to list all vouchers.");
    }

    @Override
    public void proc() {
        voucherService.findAll().forEach(System.out::println);
    }
}
