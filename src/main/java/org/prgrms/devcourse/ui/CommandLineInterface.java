package org.prgrms.devcourse.ui;

import org.prgrms.devcourse.domain.BlackUser;
import org.prgrms.devcourse.domain.Voucher;

import java.util.List;
import java.util.Scanner;

public class CommandLineInterface implements UserInterface {

    private Scanner scanner;

    public CommandLineInterface() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public String input() {
        return scanner.nextLine();
    }

    @Override
    public void showVoucherProgramMenuInterface() {
        System.out.println("=== Voucher Program ===");
        System.out.println("Type exit to exit the program.");
        System.out.println("Type create to create a new voucher");
        System.out.println("Type list to list all vouchers");
        System.out.println("Type blacklist to list black user");
    }

    @Override
    public void showVoucherTypeSelectMessage() {
        System.out.println("Type voucher type number.");
        System.out.println("1: FixedAmountVoucher, 2: PercentDiscountVoucher");
    }

    @Override
    public void showVoucherDiscountValueInputMessage() {
        System.out.println("Type discount value");
    }

    @Override
    public void showVoucherProgramTerminateMessage() {
        System.out.println("Terminate Voucher Program..");
    }

    @Override
    public void showInvalidInputMessage() {
        System.out.println("\nInvalid Input.");
        System.out.println("Type valid message.\n");
    }

    @Override
    public void showVoucherList(List<Voucher> voucherList) {
        System.out.println("-------------------------------");
        for (int i = 0; i < voucherList.size(); ++i) {
            Voucher voucher = voucherList.get(i);
            System.out.print(voucher);
        }
        System.out.println("-------------------------------");
    }

    @Override
    public void showBlackList(List<BlackUser> blackList) {
        System.out.println("-------------------------------");
        for (int i = 0; i < blackList.size(); ++i) {
            BlackUser blackUser = blackList.get(i);
            System.out.println(blackUser);
        }
        System.out.println("-------------------------------");
    }
}
