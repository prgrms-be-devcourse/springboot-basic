package org.programmers.kdtspring.ConsoleIO;

import org.programmers.kdtspring.entity.voucher.Voucher;
import org.programmers.kdtspring.repository.user.BlackListUserRepository;
import org.programmers.kdtspring.repository.voucher.VoucherRepository;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

@Component
public class Console implements Input, Output {

    private final VoucherRepository voucherRepository;
    private final BlackListUserRepository blackListUserRepository;

    public Console(VoucherRepository voucherRepository, BlackListUserRepository blackListUserRepository) {
        this.voucherRepository = voucherRepository;
        this.blackListUserRepository = blackListUserRepository;
    }


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
        System.out.print("회원 가입 시 사용한 이메일을 입력해주세요. >>");
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

    @Override
    public void showBlackList() {
        List<String[]> blackList = blackListUserRepository.findAll();
        blackList.stream().map(Object::toString).forEach(System.out::println);
    }

    @Override
    public void errorMessage() {
        System.out.println("다시 입력해주세요. 잘못 입력했습니다.");
    }
}
