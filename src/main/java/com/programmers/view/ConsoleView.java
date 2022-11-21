package com.programmers.view;

import com.programmers.customer.Customer;
import com.programmers.message.Message;
import com.programmers.voucher.voucher.Voucher;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;


@Component
public class ConsoleView implements View {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String getUserCommand() {
        return scanner.nextLine().trim();
    }

    @Override
    public void printMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void printMessage(Message message) {
        System.out.println(message);
    }

    @Override
    public void printVoucher(Voucher voucher) {
        System.out.println(voucher);
        System.out.println("============================");
    }

    @Override
    public void printCustomer(Customer customer) {
        System.out.println(customer);
    }

    @Override
    public void printList(List list) {
        for (Object o : list) {
            System.out.println(o);
        }
    }
}
