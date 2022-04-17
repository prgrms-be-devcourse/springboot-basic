package org.prgrms.deukyun.voucherapp.app.menu;

import org.prgrms.deukyun.voucherapp.domain.voucher.service.VoucherService;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Scanner;

/**
 * 생성 메뉴
 */
@Component
@Order(2)
public class CreateVoucherMenu implements Menu {

    private final Scanner scanner;
    private final VoucherService voucherService;

    public CreateVoucherMenu(VoucherService voucherService) {
        this.scanner = new Scanner(System.in);
        this.voucherService = voucherService;
    }

    @Override
    public void display() {
        System.out.println("Type create to create a new voucher.");
    }

    @Override
    public void proc() {
        System.out.println("Enter the type of voucher : fixed(f),percent(p)");
        String type = scanner.nextLine();
    }
}
