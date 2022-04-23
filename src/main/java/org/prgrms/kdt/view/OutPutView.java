package org.prgrms.kdt.view;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.prgrms.kdt.customer.model.Customer;
import org.prgrms.kdt.voucher.model.Voucher;

public class OutPutView {

    public static void showsInitMenu() {
        Arrays.stream(Command.values()).forEach(m -> System.out.println(m.getMessage()));
    }

    public static void showsRetryMessage() {
        System.out.println("잘못 입력하셨습니다 다시 입력해주세요.\n");
    }

    public static void showsVoucherMenu() {
        System.out.println("==== Create Voucher ====");
        Arrays.stream(VoucherMenu.values()).forEach(v -> System.out.println(v.getMessage()));
    }

    public static void showsVoucherHistory(Collection<Voucher> vouchers) {
        vouchers.forEach(System.out::println);
    }

    public static void showsBlackList(List<Customer> customers) {
        customers.forEach(System.out::println);
    }

}
