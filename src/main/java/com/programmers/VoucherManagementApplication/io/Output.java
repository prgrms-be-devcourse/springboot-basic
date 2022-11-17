package com.programmers.VoucherManagementApplication.io;

import com.programmers.VoucherManagementApplication.voucher.Voucher;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
public class Output {
    public void write(String message) {
        System.out.println(message);
    }

    public void writeAll(Map<UUID, Voucher> map) {
        map.forEach((key, value) -> System.out.println(value));
    }
}
