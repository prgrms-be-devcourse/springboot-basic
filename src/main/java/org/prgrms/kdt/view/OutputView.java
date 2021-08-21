package org.prgrms.kdt.view;

import org.prgrms.kdt.member.domain.Member;
import org.prgrms.kdt.voucher.domain.Voucher;

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

    public static void showAllBlackList(Map<UUID, Member> members) {
        System.out.println("Show Members");
        members.forEach((uuid, member) -> System.out.println(member.toString()));
    }

}
