package org.prgrms.kdt.commendLine;

import org.prgrms.kdt.member.domain.Member;
import org.prgrms.kdt.voucher.domain.Voucher;
import java.util.List;

public class ConsoleOutput {
    public static void printMenu() {
        System.out.println("Type -exit- to exit the program.\n" +
                "Type -create- to create a new voucher.\n" +
                "Type -list- to list all vouchers.\n" +
                "Type -blacklist- to list all blackList");
    }

    public static void printVoucherTypes() {
        System.out.println("1. FixedAmountVoucher\n" +
                "2. PercentDiscountVoucher");
    }

    public static void printAllBoucher(List<Voucher> vouchers) {
        vouchers.stream()
                .forEach(e -> System.out.println(e.getVoucherType()));
        System.out.println();
    }

    public static void printAllBlackList(List<Member> blackList){
        blackList.stream()
                .forEach(e -> System.out.println(e.getMemberName()));
        System.out.println();
    }

    public static void printMessage(String str){
        System.out.println(str);
    }

    public static void printError(){
        System.out.println("잘못된 입력입니다. 다시 입력해 주십시오\n");
    }
}
