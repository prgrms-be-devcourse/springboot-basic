package com.programmers.assignment.voucher.engine.io;

import com.programmers.assignment.voucher.engine.voucher.Voucher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConsoleOutput {
    public void findVoucherList(List<Voucher> voucherList) {
        System.out.println("======= Voucher List=========");
        voucherList.forEach(System.out::println);
        System.out.println("=============================");
    }

    public void exitApplication() {
        System.out.println("===Exit Application===");
    }

    public void voucherListSizeZero() {
        System.out.println("There is nothing any vouchers");
    }
}
