package com.prgrms.io;

import com.prgrms.model.voucher.VoucherPolicy;

import java.util.Optional;
import java.util.Scanner;

public class Input {
    private final Scanner sc = new Scanner(System.in);
    public Optional<Menu> enterMenu() {
        String selectedMenu = sc.next();
        return Menu.findBySelectedMenu(selectedMenu);
    }

    public Optional<VoucherPolicy> enterVoucherPolicy() {
        String selectedPolicy = sc.next();
        return VoucherPolicy.findBySelectedPolicy(selectedPolicy);
    }

    public Long enterDiscount() {
        Long discount = sc.nextLong();
        return discount;
    }
}
