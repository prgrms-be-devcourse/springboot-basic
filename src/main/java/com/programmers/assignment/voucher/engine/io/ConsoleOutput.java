package com.programmers.assignment.voucher.engine.io;

import com.programmers.assignment.voucher.engine.voucher.Voucher;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
public class ConsoleOutput {
    public void findVoucherList(Map<UUID, Voucher> map) {
        System.out.println("======= Voucher List=========");
        map.forEach((k, v) -> System.out.println(v)
        );
        System.out.println("=============================");
    }

    public void exitApplication() {
        System.out.println("===Exit Application===");
    }
}
