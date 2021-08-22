package org.prgrms.orderApp.CMDApplication.console;

import org.prgrms.orderApp.model.voucher.Voucher;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class Console implements Input, Output{
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String input(String scipt) {
        System.out.print(scipt);
        return scanner.nextLine();
    }

    @Override
    public void voucherList(List<Voucher> voucherList) {
        voucherList.forEach(System.out::println);
    }

    @Override
    public void errorMessage(String errorMessage) {
        System.out.println(errorMessage);
    }

    @Override
    public void infoMessage(String infoMessage) {
        System.out.println(infoMessage);
    }

    @Override
    public void print(String message){
        System.out.println(message);
    }
}
