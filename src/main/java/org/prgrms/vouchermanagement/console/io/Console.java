package org.prgrms.vouchermanagement.console.io;

import org.prgrms.vouchermanagement.domain.Voucher;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class Console implements Input, Output {

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public Menu inputMenu() {
        return Menu.getMenu(scanner.nextLine());
    }

    @Override
    public void printMenu() {
        System.out.println(Menu.EXIT_PROGRAM.getMessage());
        System.out.println(Menu.CREATE_VOUCHER.getMessage());
        System.out.println(Menu.LIST_VOUCHERS.getMessage());
        System.out.println(Menu.LIST_BLACK_CUSTOMERS.getMessage());
    }

    @Override
    public void printVouchers(List<Voucher> vouchers) {
        vouchers.forEach(System.out::println);
    }

    @Override
    public void printMenuError() {
        System.out.println(Menu.INVALID_MENU_INPUT.getMessage());
    }
}
