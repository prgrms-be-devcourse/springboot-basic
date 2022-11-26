package com.programmers.VoucherManagementApplication.io;

import com.programmers.VoucherManagementApplication.voucher.Voucher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Output {
    public void write(String message) {
        System.out.println(message);
    }

    public void writeAll(List<Voucher> list) {
        list.forEach(System.out::println);
    }
}
