package org.programmers.kdtspring.ConsoleIO;

import org.programmers.kdtspring.entity.voucher.Voucher;
import org.programmers.kdtspring.repository.voucher.VoucherRepository;

import java.util.List;
import java.util.Scanner;

public class Console implements Input, Output {

    private final VoucherRepository voucherRepository;

    public Console(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }


    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String showOption() {
        System.out.println("==== Voucher Program ====");
        System.out.println("Type 'exit' to exit the program");
        System.out.println("Type 'create' to create a new voucher");
        System.out.println("Type 'list' to list a new voucher");
        System.out.println();

        System.out.print("Your command => ");

        String input;
        input = scanner.nextLine();
        return input;
    }

    @Override
    public String chooseVoucher() {
        System.out.println("==== Choose the Voucher you wanna create ====");
        System.out.println("1. FixedVoucher");
        System.out.println("2. PercentDiscountVoucher");
        System.out.print("Your Choice is ... = ");

        return scanner.nextLine();
    }

    @Override
    public void voucherCreated() {
        System.out.println("Voucher created!");
    }

    @Override
    public void showAllVoucher() {
        List<Voucher> voucherList = voucherRepository.findAll();
        voucherList.forEach(System.out::println);
    }
}
