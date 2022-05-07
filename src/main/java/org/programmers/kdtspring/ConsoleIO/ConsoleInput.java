package org.programmers.kdtspring.ConsoleIO;

import org.programmers.kdtspring.service.BlackListUserService;
import org.programmers.kdtspring.service.CustomerService;
import org.programmers.kdtspring.service.VoucherService;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleInput implements Input {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String showOption() {
        System.out.println("==== Voucher Program ====");
        System.out.println("Type 'exit' to exit the program");
        System.out.println("Type 'create' to create a new voucher");
        System.out.println("Type 'list' to list a new voucher");
        System.out.println("Type 'append' to append blackListUser");
        System.out.println("Type 'listForOneCustomer' to show vouchers belonged to one customer");
        System.out.println();

        System.out.print("Your command => ");

        return scanner.nextLine();
    }

    @Override
    public String chooseVoucher() {
        System.out.println("==== Choose the Voucher you wanna create ====");
        System.out.println("1. FixedAmountVoucher");
        System.out.println("2. PercentDiscountVoucher");
        System.out.print("Your Choice is ... = ");

        return scanner.nextLine();
    }

    @Override
    public String inputEmail() {
        System.out.print("가입 시 사용한 이메일을 입력해주세요. >> ");
        return scanner.nextLine();
    }

    @Override
    public int inputDiscount() {
        return scanner.nextInt();
    }
}