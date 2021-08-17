package org.prgrms.kdt.view;

import org.prgrms.kdt.voucher.Voucher;

import java.util.List;

public class OutputView {

    public static void exit() {
        System.out.println("Bye~");
        System.exit(0);
    }

    public static void showVouchers(List<Voucher> vouchers) {
        System.out.println("Show Vouchers");
        vouchers.stream()
                .map(Voucher::toString)
                .forEach(System.out::println);
    }

}
