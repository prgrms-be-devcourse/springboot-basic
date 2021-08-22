package com.prgrm.kdt.view;

import com.prgrm.kdt.voucher.domain.Voucher;

import java.util.Map;
import java.util.UUID;

public class OutputView {

    public static void exit() {
        System.out.println("=== exit program ===");
        System.exit(0);
    }

    public static void showVoucherList(Map<UUID, Voucher> vouchers) {
        System.out.println("=== Show Vouchers ===");
        vouchers.forEach((uuid, voucher) -> System.out.println(voucher.toString()));
    }

}
