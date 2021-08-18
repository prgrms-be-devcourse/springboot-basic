package org.prgrms.kdt.view;

import org.prgrms.kdt.voucher.Voucher;

import java.util.Map;
import java.util.UUID;

public class OutputView {

    public static void exit() {
        System.out.println("Bye~");
        System.exit(0);
    }

    public static void showVouchers(Map<UUID, Voucher> vouchers) {
        System.out.println("Show Vouchers");
        vouchers.forEach((uuid, voucher) -> System.out.println(voucher.toString()));
    }

}
