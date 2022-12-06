package com.programmers.voucher.io;

import com.programmers.voucher.model.voucher.Voucher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Output {
    public void print(Message message) {
        System.out.println(message);
    }

    public void printError(String message) {
        System.out.println(message);
    }

    public void printVoucher(Voucher voucher) {
        System.out.println(voucher);
    }

    public void printVouchers(List<Voucher> vouchers) {
        vouchers.forEach(System.out::println);
    }

    public void printFormat(Message message, String name) {
        System.out.printf(message.toString(), name);
    }
}
