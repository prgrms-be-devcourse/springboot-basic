package com.programmers.assignment.voucher.engine.io;

import com.programmers.assignment.voucher.engine.voucher.Voucher;

import java.util.Map;
import java.util.UUID;

public class ConsoleOutput implements Output {
    @Override
    public void findVoucherList(Map<UUID, Voucher> map) {
        System.out.println("======= Voucher List=========");
        map.forEach((k,v) -> System.out.println(v)
    );
        System.out.println("=============================");
    }

    @Override
    public void exitApplication() {
        System.out.println("===Exit Application===");
    }
}
