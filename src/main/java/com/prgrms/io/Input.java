package com.prgrms.io;

import com.prgrms.model.voucher.VoucherType;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Scanner;

@Component
public class Input {
    private final Scanner sc = new Scanner(System.in);

    public Optional<Menu> enterMenu() {
        String selectedMenu = sc.next();
        return Menu.findByMenu(selectedMenu);
    }

    public Optional<VoucherType> enterVoucherPolicy() {
        String selectedPolicy = sc.next();
        return VoucherType.findByPolicy(selectedPolicy);
    }

    public Long enterDiscount() {
        Long discount = sc.nextLong();
        return discount;
    }
}
