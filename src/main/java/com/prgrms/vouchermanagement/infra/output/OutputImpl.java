package com.prgrms.vouchermanagement.infra.output;

import org.springframework.stereotype.Component;

@Component
public class OutputImpl implements OutputProvider {
    @Override
    public void showMenu() {
        System.out.println("=== Voucher Program ===");
        System.out.println("Type exit to exit the program.");
        System.out.println("Type create to create a new voucher.");
        System.out.println("Type list to list all vouchers.");
    }

}
