package org.prgrms.kdt.view;

import java.util.Collection;
import org.prgrms.kdt.voucher.model.Voucher;

public class OutPutView {

    public static void showsInitMenu() {
        System.out.println("==== Voucher Program ====");
        System.out.println("Type exit to exit the program.");
        System.out.println("Type create to create a new voucher.");
        System.out.println("Type list to list all vouchers.");
    }

    public static void showsRetryMessage() {
        System.out.println("잘못 입력하셨습니다 다시 입력해주세요.\n");
    }

    public static void showsVoucherMenu() {
        System.out.println("==== Create Voucher ====");
        System.out.println("ex_1) fixed 1000");
        System.out.println("ex_2) percent 15");
    }

    public static void showsVoucherHistory(Collection<Voucher> vouchers) {
        vouchers.forEach(System.out::println);
    }

}
