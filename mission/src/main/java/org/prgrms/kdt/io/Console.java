package org.prgrms.kdt.io;

import org.springframework.stereotype.Component;

@Component
public class Console implements Input, Output{
    @Override
    public void printMenu() {
        System.out.println("===Voucher Program===");
        System.out.println("Type exit to exit the program.");
        System.out.println("Type create to create a new voucher.");
        System.out.println("Type list to list all vouchers.");
    }

    @Override
    public void typeMenu() {

    }
}
